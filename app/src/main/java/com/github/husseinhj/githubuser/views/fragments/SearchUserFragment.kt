package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.github.husseinhj.githubuser.R
import android.widget.AdapterView.OnItemClickListener
import com.github.husseinhj.githubuser.bases.BaseFragment
import com.github.husseinhj.githubuser.extensions.debounce
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import com.github.husseinhj.githubuser.consts.GITHUB_USERNAME
import com.github.husseinhj.githubuser.models.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.adapters.UserSearchResultAdapter
import com.github.husseinhj.githubuser.viewmodels.fragments.ErrorEnumType
import com.github.husseinhj.githubuser.databinding.FragmentSearchUserBinding
import com.github.husseinhj.githubuser.viewmodels.fragments.SearchUserViewModel
import com.github.husseinhj.githubuser.viewmodels.fragments.SearchUserViewModelState

class SearchUserFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchUserBinding
    private val viewModel by stateViewModel<SearchUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        this.listenToSearchBarTextChanges()
        binding = FragmentSearchUserBinding.inflate(inflater)

        this.enableBackButton(true)
        this.setTitle(getString(R.string.user_search_title))

        binding.searchResultGridView.onItemClickListener = onSearchResultItemClicked()
        viewModel.data.observe(this.viewLifecycleOwner, observedOnDataState())

        return binding.root
    }

    private fun onSearchResultItemClicked(): OnItemClickListener {
        return OnItemClickListener { _, _, position, _ ->
            val state = viewModel.data.value as? SearchUserViewModelState.SearchedResult
            val models = state?.dataset
            models?.let {
                navigateToUserDetail(it[position])
            }
        }
    }

    private fun observedOnDataState(): (t: SearchUserViewModelState) -> Unit =
        {
            when (it) {
                is SearchUserViewModelState.NotSearchedYet -> {
                    hideLoading()
                    showPlaceholderMessage(getString(R.string.not_searched_yet))

                    binding.searchResultGridView.adapter = null
                }
                is SearchUserViewModelState.Loading -> {
                    showLoading()
                    hidePlaceholderMessage()
                    binding.searchResultGridView.adapter = null
                }
                is SearchUserViewModelState.SearchedResult -> {
                    showContent(it)
                }
                is SearchUserViewModelState.Error -> {
                    showErrorMessage(it)
                    binding.searchResultGridView.adapter = null
                }
                else -> {}
            }
        }

    private fun showContent(it: SearchUserViewModelState.SearchedResult) {
        hideLoading()
        hidePlaceholderMessage()

        showResultData(it)
    }

    private fun showErrorMessage(it: SearchUserViewModelState.Error) {
        hideLoading()
        hidePlaceholderMessage()

        when (it.errorType) {
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

    private fun showResultData(it: SearchUserViewModelState.SearchedResult) {
        binding.searchResultGridView.adapter = UserSearchResultAdapter(it.dataset)
        binding.searchResultGridView.deferNotifyDataSetChanged()
    }

    private fun hidePlaceholderMessage() {
        binding.messageLabel.isVisible = false
    }

    private fun showLoading() {
        binding.loadingView.isVisible = true
    }

    private fun showPlaceholderMessage(message: String) {
        binding.messageLabel.text = message
        binding.messageLabel.isVisible = true
    }

    private fun hideLoading() {
        binding.loadingView.isVisible = false
    }

    private fun listenToSearchBarTextChanges() {
        this.setOnSearchBarTextChangedListener { query ->
            query.debounce { debouncedWord ->
                viewModel.searchUser(debouncedWord)
            }
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