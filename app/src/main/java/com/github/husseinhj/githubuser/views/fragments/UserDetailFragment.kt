package com.github.husseinhj.githubuser.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.consts.GITHUB_USERNAME
import com.github.husseinhj.githubuser.databinding.FragmentUserDetailBinding
import com.github.husseinhj.githubuser.extensions.downloadAndShowImage
import com.github.husseinhj.githubuser.models.eventbus.OnBackButtonVisibilityMessage
import com.github.husseinhj.githubuser.viewmodels.fragments.UserDetailViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.greenrobot.eventbus.EventBus

class UserDetailFragment : Fragment() {
    private var usernameParam: String? = null
    private var viewModel = UserDetailViewModel()
    private lateinit var binding: FragmentUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usernameParam = it.getString(GITHUB_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_detail,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.serverErrorMessage.observe(this) { message ->
            context?.let { showErrorAlert(message, it) }
        }
        viewModel.userAvatarUrl.observe(this) { avatarUrl ->
            applyImageToView(avatarUrl, binding.userAvatarView)
        }
        usernameParam?.let { viewModel.getUserDetail(it) }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        EventBus.getDefault().post(OnBackButtonVisibilityMessage(true))
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().post(OnBackButtonVisibilityMessage(false))
    }

    private fun showErrorAlert(errorMessage: String?, context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(getString(R.string.error))
            .setMessage(getString(R.string.server_error_message, errorMessage ?: ""))
            .setPositiveButton(getString(R.string.ok_button)) { _, _ ->
                NavHostFragment.findNavController(this).navigateUp()
            }
            .show()
    }

    fun applyImageToView(imageUrl: String?, view: ImageView) {
        view.downloadAndShowImage(imageUrl)
    }
}