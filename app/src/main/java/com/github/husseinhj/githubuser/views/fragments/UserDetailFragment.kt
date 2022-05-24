package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.content.Context
import android.widget.ImageView
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.github.husseinhj.githubuser.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.github.husseinhj.githubuser.bases.BaseFragment
import com.github.husseinhj.githubuser.extensions.toString
import com.github.husseinhj.githubuser.extensions.navigateUp
import com.github.husseinhj.githubuser.consts.GITHUB_USERNAME
import com.github.husseinhj.githubuser.extensions.isoStringToDate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.github.husseinhj.githubuser.extensions.downloadAndShowImage
import com.github.husseinhj.githubuser.consts.ANDROID_DEEPLINK_INTENT_KEY
import com.github.husseinhj.githubuser.databinding.FragmentUserDetailBinding
import com.github.husseinhj.githubuser.viewmodels.fragments.UserDetailViewModel
import com.github.husseinhj.githubuser.viewmodels.fragments.UserDetailViewModelState

class UserDetailFragment : BaseFragment() {
    private var usernameParam: String? = null
    private var cameFromDeeplink: Boolean = false
    private lateinit var binding: FragmentUserDetailBinding
    private val viewModel by viewModel<UserDetailViewModel>()

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

        binding = FragmentUserDetailBinding.inflate(inflater)
        viewModel.data.observe(viewLifecycleOwner) {
            when(it) {
                is UserDetailViewModelState.Loading -> {
                    showLoading()
                }
                is UserDetailViewModelState.Data -> {
                    hideLoading()

                    setContent(it)
                }
                is UserDetailViewModelState.Error -> {
                    hideLoading()

                    context?.let { it1 -> showErrorAlert(it.message, it1) }
                }
            }
        }

        usernameParam?.let { viewModel.getUserDetail(it) }

        return binding.root
    }

    private fun showLoading() {
        binding.loadingView.isVisible = true
    }

    private fun hideLoading() {
        binding.loadingView.isVisible = false
    }

    private fun setContent(it: UserDetailViewModelState.Data) {
        it.userDetail.apply {
            binding.userFullNameView.text = name
            binding.userBioView.text = bio ?: ""
            binding.followersView.text = followers.toString()
            binding.followingView.text = following.toString()
            applyImageToView(avatarURL, binding.userAvatarView)
            binding.usernameView.text = String.format("@%s", login ?: "")

            binding.emailView.text = email
            binding.emailView.isVisible = !email.isNullOrEmpty()

            binding.locationView.text = location
            binding.locationView.isVisible = !location.isNullOrEmpty()

            binding.organizationView.text = company
            binding.organizationView.isVisible = !company.isNullOrEmpty()

            val date = createdAt?.isoStringToDate()
            binding.jointView.isVisible = !createdAt.isNullOrEmpty()
            binding.jointView.text = date?.toString("MMMM yyyy") ?: ""
        }
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