package com.github.husseinhj.githubuser.bases

import android.os.Bundle
import android.content.Context
import androidx.fragment.app.Fragment
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.extensions.navigate
import com.github.husseinhj.githubuser.utils.ToolbarAppearance
import com.github.husseinhj.githubuser.utils.OnTextChangedListener
import com.github.husseinhj.githubuser.utils.OnFocusChangedListener
import com.github.husseinhj.githubuser.views.activities.MainActivity

open class BaseFragment: Fragment() {
    private var title: String? = null
    private var toolbarAppearance: ToolbarAppearance? = null
    private var searchBarTextChanged: OnTextChangedListener? = null
    private var searchBarFocusListener: OnFocusChangedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        toolbarAppearance = (this.requireActivity() as? MainActivity)?.toolbarAppearance
    }

    override fun onStart() {
        super.onStart()

        toolbarAppearance?.setTitle(title)

        toolbarAppearance?.setOnFocusListener {
            this.searchBarFocusListener?.invoke(it)
        }
        toolbarAppearance?.setOnTextChangedListener {
            this.searchBarTextChanged?.invoke(it)
        }
    }

    fun setOnSearchBarFocusListener(listener: OnFocusChangedListener) {
        this.searchBarFocusListener = listener
    }

    fun setOnSearchBarTextChangedListener(listener: OnTextChangedListener) {
        this.searchBarTextChanged = listener
    }

    fun navigateFromHomeToSearchFragment() {
        try {
            this.navigate(
                to = R.id.action_homeFragment_to_searchUserFragment,
                title = getString(R.string.user_search_title),
                showSoftBackButton = true
            )
        } catch (e: Exception) {
            println("Could not navigate for ${e.message}")
        }
        toolbarAppearance?.expandSearchBar()
        toolbarAppearance?.focusOnSearchBar()
    }

    fun navigateFromDetailToSearchFragment() {
        this.navigate(
            to = R.id.action_userDetailFragment_to_searchUserFragment,
            title = getString(R.string.user_search_title),
            showSoftBackButton = true
        )
    }

    fun navigateFromSearchToDetailFragment(data: Bundle) {
        toolbarAppearance?.collapseSearchBar()
        toolbarAppearance?.clearFocusOnSearchBar()

        this.navigate(
            data = data,
            to = R.id.action_searchUserFragment_to_userDetailFragment,
            title = getString(R.string.user_profile_title),
            showSoftBackButton = true
        )
    }

    fun setTitle(pageTitle: String) {
        this.title = pageTitle
    }

    fun enableBackButton(enable: Boolean) {
        toolbarAppearance?.setShowBackSoftButton(enable)
    }
}