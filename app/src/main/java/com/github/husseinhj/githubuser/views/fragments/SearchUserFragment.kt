package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.consts.GITHUB_USERNAME
import com.github.husseinhj.githubuser.databinding.FragmentSearchUserBinding
import com.github.husseinhj.githubuser.extensions.navigate
import com.github.husseinhj.githubuser.models.data.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.models.eventbus.OnBackButtonVisibilityMessage
import com.github.husseinhj.githubuser.models.eventbus.OnSearchBarMessage
import com.github.husseinhj.githubuser.viewmodels.fragments.ErrorEnumType
import com.github.husseinhj.githubuser.viewmodels.fragments.SearchUserViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchUserFragment : Fragment() {

    private var viewModel = SearchUserViewModel()
    private lateinit var binding: FragmentSearchUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_user,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        EventBus.getDefault().register(this)
        handleErrorPlaceholder()
        viewModel.setOnUserCellClickedListener {
            navigateToUserDetail(it)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        EventBus.getDefault().post(OnBackButtonVisibilityMessage(true))
    }

    override fun onStop() {
        super.onStop()

        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().post(OnBackButtonVisibilityMessage(false))
    }

    @Subscribe
    fun onSearchBarMessage(message: OnSearchBarMessage) {
        if (message.focused != null) {
            return
        }

        context?.let { viewModel.searchUser(message.query, it) }
    }

    private fun handleErrorPlaceholder() {
        viewModel.errorType.observe(this) { errorType ->
            when (errorType) {
                ErrorEnumType.NETWORK -> {
                    showErrorPlaceholder(
                        R.string.you_are_not_connected_to_internet,
                        R.drawable.ic_wifi_off
                    )
                }
                ErrorEnumType.SERVER -> {
                    showErrorPlaceholder(
                        R.string.server_error_message,
                        R.drawable.ic_error
                    )
                }
                else -> {}
            }
        }

        viewModel.errorPlaceholderVisibility.observe(this) {
            binding.placeholderLayout.root.visibility = it
        }
    }

    private fun showErrorPlaceholder(messageResId: Int, iconResId: Int) {
        binding.placeholderLayout.placeholderView.apply {
            text = getString(messageResId)
        }
        binding.placeholderLayout.placeholderView.setCompoundDrawablesRelativeWithIntrinsicBounds(
            0,
            iconResId,
            0,
            0
        )
    }

    private fun navigateToUserDetail(userModel: UserSimpleDetailsModel) {
        EventBus.getDefault().post(OnSearchBarMessage(focused = false))

        val data = Bundle()
        data.putString(GITHUB_USERNAME, userModel.login)

        this.navigate(
            R.id.action_searchUserFragment_to_userDetailFragment,
            data
        )
    }
}