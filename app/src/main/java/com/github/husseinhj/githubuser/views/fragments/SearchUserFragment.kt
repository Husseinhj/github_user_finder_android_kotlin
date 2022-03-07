package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.github.husseinhj.githubuser.R
import androidx.databinding.DataBindingUtil
import com.github.husseinhj.githubuser.bases.BaseFragment
import com.github.husseinhj.githubuser.extensions.debounce
import com.github.husseinhj.githubuser.consts.GITHUB_USERNAME
import com.github.husseinhj.githubuser.models.data.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.viewmodels.fragments.ErrorEnumType
import com.github.husseinhj.githubuser.databinding.FragmentSearchUserBinding
import com.github.husseinhj.githubuser.viewmodels.fragments.SearchUserViewModel

class SearchUserFragment : BaseFragment() {

    private var viewModel = SearchUserViewModel()
    private lateinit var binding: FragmentSearchUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        title = getString(R.string.user_search_title)

        if (::binding.isInitialized) {
            return binding.root
        }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_user,
            container,
            false
        )

        viewModel = SearchUserViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        handleErrorPlaceholder()
        binding.searchResultGridView.setOnItemClickListener { _, _, position, _ ->
            val model = viewModel.dataset?.get(position)
            model?.let { navigateToUserDetail(it) }
        }

        return binding.root
    }

        toolbarAppearance?.setOnTextChangedListener { query ->
            query.debounce { debouncedWord ->
                context?.let { viewModel.searchUser(debouncedWord, it) }
            }
        }

        return binding.root
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
        val data = Bundle()
        data.putString(GITHUB_USERNAME, userModel.login)

        this.navigateFromSearchToDetailFragment(data)
    }
}