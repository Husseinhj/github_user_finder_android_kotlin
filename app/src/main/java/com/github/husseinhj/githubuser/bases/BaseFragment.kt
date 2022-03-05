package com.github.husseinhj.githubuser.bases

import android.os.Bundle
import android.content.Context
import androidx.fragment.app.Fragment
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.extensions.navigate
import com.github.husseinhj.githubuser.utils.ToolbarAppearance
import com.github.husseinhj.githubuser.views.activities.MainActivity

open class BaseFragment: Fragment() {
    internal var title: String? = null
    internal var toolbarAppearance: ToolbarAppearance? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        toolbarAppearance = (this.requireActivity() as? MainActivity)?.toolbarAppearance
    }

    override fun onStart() {
        super.onStart()

        toolbarAppearance?.setTitle(title)
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
}