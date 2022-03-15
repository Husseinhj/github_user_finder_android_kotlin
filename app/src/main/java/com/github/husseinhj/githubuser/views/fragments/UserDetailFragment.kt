package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.content.Context
import android.widget.ImageView
import android.view.LayoutInflater
import com.github.husseinhj.githubuser.R
import androidx.databinding.DataBindingUtil
import com.github.husseinhj.githubuser.bases.BaseFragment
import com.github.husseinhj.githubuser.extensions.navigateUp
import com.github.husseinhj.githubuser.consts.GITHUB_USERNAME
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.github.husseinhj.githubuser.extensions.downloadAndShowImage
import com.github.husseinhj.githubuser.consts.ANDROID_DEEPLINK_INTENT_KEY
import com.github.husseinhj.githubuser.databinding.FragmentUserDetailBinding
import com.github.husseinhj.githubuser.viewmodels.fragments.UserDetailViewModel

class UserDetailFragment : BaseFragment() {
    private var usernameParam: String? = null
    private var cameFromDeeplink: Boolean = false
    private lateinit var binding: FragmentUserDetailBinding
    private val profileViewModel by stateViewModel<UserDetailViewModel>(
        state = { arguments ?: Bundle.EMPTY }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            cameFromDeeplink = hasDeeplinkIntent(it)
            usernameParam = it.getString(GITHUB_USERNAME)
        }

        if (usernameParam == null && cameFromDeeplink) {
            usernameParam = getUsernameFromDeepLinkIntentAsFallback(arguments)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        profileViewModel.saveState()

        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        this.enableBackButton(true)
        this.setTitle(getString(R.string.user_profile_title))

        this.setOnSearchBarFocusListener {
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

        binding.viewModel = profileViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        profileViewModel.serverErrorMessage.observe(this) { message ->
            if (message == null) {
                return@observe
            }

            context?.let { showErrorAlert(message, it) }
        }
        profileViewModel.userAvatarUrl.observe(this) { avatarUrl ->
            if (avatarUrl == null) {
                return@observe
            }
            applyImageToView(avatarUrl, binding.userAvatarView)
        }
        usernameParam?.let { profileViewModel.getUserDetail(it) }

        return binding.root
    }

    private fun getUsernameFromDeepLinkIntentAsFallback(arg: Bundle?): String? {
        val intent = arg?.get(ANDROID_DEEPLINK_INTENT_KEY) as? Intent
        val paths = intent?.data?.path?.split("/")
        if ((paths?.size ?: 0) > 1) {
            return paths?.get(1)
        }

        return null
    }

    private fun hasDeeplinkIntent(it: Bundle) =
        it.containsKey(ANDROID_DEEPLINK_INTENT_KEY)

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