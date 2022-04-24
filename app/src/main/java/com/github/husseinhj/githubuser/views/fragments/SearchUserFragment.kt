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
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import com.github.husseinhj.githubuser.models.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.viewmodels.fragments.ErrorEnumType
import com.github.husseinhj.githubuser.databinding.FragmentSearchUserBinding
import com.github.husseinhj.githubuser.viewmodels.fragments.SearchUserViewModel

class SearchUserFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchUserBinding
    private val searchUserViewModel by stateViewModel<SearchUserViewModel>(
        state = { arguments ?: Bundle.EMPTY }
    )

    override fun onSaveInstanceState(outState: Bundle) {
        searchUserViewModel.saveState()

        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        listenToSearchBarTextChanges()
        this.enableBackButton(true)
        this.setTitle(getString(R.string.user_search_title))

        if (::binding.isInitialized) {
            feedBindingObject()
            return binding.root
        }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_user,
            container,
            false
        )

        feedBindingObject()
        searchUserViewModel.resultAdapter.observe(this) {
            binding.searchResultGridView.deferNotifyDataSetChanged()
        }

        handleErrorPlaceholder()
        binding.searchResultGridView.setOnItemClickListener { _, _, position, _ ->
            val model = searchUserViewModel.dataset?.get(position)
            model?.let { navigateToUserDetail(it) }
        }

        return binding.root
    }

    private fun feedBindingObject() {
        binding.viewModel = searchUserViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    private fun listenToSearchBarTextChanges() {
        this.setOnSearchBarTextChangedListener { query ->
            query.debounce { debouncedWord ->
                context?.let { searchUserViewModel.searchUser(debouncedWord, it) }
            }
        }
    }

    private fun handleErrorPlaceholder() {
        searchUserViewModel.errorType.observe(this) { errorType ->
            when (errorType) {
                ErrorEnumType.NETWORK -> {
                    showErrorPlaceholder(
                        getString(R.string.you_are_not_connected_to_internet),
                        R.drawable.ic_wifi_off
                    )
                }
                ErrorEnumType.SERVER -> {
                    showErrorPlaceholder(
                        getString(R.string.server_error_message, ""),
                        R.drawable.ic_error
                    )
                }
                else -> {}
            }
        }

        searchUserViewModel.errorPlaceholderVisibility.observe(this) {
            binding.placeholderLayout.root.visibility = it
        }
    }

    private fun showErrorPlaceholder(message: String, iconResId: Int) {
        binding.placeholderLayout.placeholderView.apply {
            text = message
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