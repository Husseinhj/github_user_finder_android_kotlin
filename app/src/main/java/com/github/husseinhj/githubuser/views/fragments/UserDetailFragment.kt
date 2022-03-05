package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.content.Context
import android.view.LayoutInflater
import com.github.husseinhj.githubuser.R
import androidx.databinding.DataBindingUtil
import com.github.husseinhj.githubuser.bases.BaseFragment
import com.github.husseinhj.githubuser.extensions.navigateUp
import com.github.husseinhj.githubuser.consts.GITHUB_USERNAME
import com.github.husseinhj.githubuser.extensions.showSoftBackButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.github.husseinhj.githubuser.extensions.downloadAndShowImage
import com.github.husseinhj.githubuser.databinding.FragmentUserDetailBinding
import com.github.husseinhj.githubuser.viewmodels.fragments.UserDetailViewModel

class UserDetailFragment : BaseFragment() {
    private var usernameParam: String? = null
    private var cameFromDeeplink: Boolean = false
    private var viewModel = UserDetailViewModel()
    private lateinit var binding: FragmentUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            usernameParam = it.getString(GITHUB_USERNAME)
            cameFromDeeplink = hasDeeplinkIntent(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        title = getString(R.string.user_profile_title)
        this.toolbarAppearance?.setOnFocusListener {
            if (cameFromDeeplink) {
                this.navigateFromDetailToSearchFragment()
            } else {
                this.navigateUp()
            }
        }

        if (::binding.isInitialized) {
            return binding.root
        }

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

        this.showSoftBackButton(true)
    }

    private fun hasDeeplinkIntent(it: Bundle) =
        it.containsKey("android-support-nav:controller:deepLinkIntent")

    private fun showErrorAlert(errorMessage: String?, context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(getString(R.string.error))
            .setMessage(getString(R.string.server_error_message, errorMessage ?: ""))
            .setPositiveButton(getString(R.string.ok_button)) { _, _ ->
                this.navigateUp()
            }
            .show()
    }

    private fun applyImageToView(imageUrl: String?, view: ImageView) {
        view.downloadAndShowImage(imageUrl)
    }
}