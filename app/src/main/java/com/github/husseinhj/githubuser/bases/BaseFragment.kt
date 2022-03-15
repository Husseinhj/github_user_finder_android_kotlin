package com.github.husseinhj.githubuser.bases

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.husseinhj.githubuser.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import com.github.husseinhj.githubuser.extensions.navigate
import com.github.husseinhj.githubuser.utils.ToolbarAppearance
import com.github.husseinhj.githubuser.utils.NavigationAppearance
import com.github.husseinhj.githubuser.utils.OnTextChangedListener
import com.github.husseinhj.githubuser.utils.OnFocusChangedListener

open class BaseFragment: Fragment() {
    private val toolbarAppearance: ToolbarAppearance by inject {
        parametersOf(this.activity, R.menu.main_menu)
    }
    private var searchBarTextChanged: OnTextChangedListener? = null
    private val navigationAppearance: NavigationAppearance by inject {
        parametersOf(this.activity)
    }
    private var searchBarFocusListener: OnFocusChangedListener? = null

    override fun onStart() {
        super.onStart()

        toolbarAppearance.setOnFocusListener {
            this.searchBarFocusListener?.invoke(it)
        }
        toolbarAppearance.setOnTextChangedListener {
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
            this.navigate(to = R.id.action_homeFragment_to_searchUserFragment)
        } catch (e: Exception) {
            println("Could not navigate for ${e.message}")
        }
        toolbarAppearance.expandSearchBar()
        toolbarAppearance.focusOnSearchBar()
    }

    fun navigateFromDetailToSearchFragment() {
        this.navigate(to = R.id.action_userDetailFragment_to_searchUserFragment)
    }

    fun navigateFromSearchToDetailFragment(data: Bundle) {
        toolbarAppearance.collapseSearchBar()
        toolbarAppearance.clearFocusOnSearchBar()

        this.navigate(
            data = data,
            to = R.id.action_searchUserFragment_to_userDetailFragment)
    }

    fun setTitle(pageTitle: String) {
        navigationAppearance.setTitle(pageTitle)
    }

    fun enableBackButton(enable: Boolean) {
        navigationAppearance.setShowBackSoftButton(enable)
    }
}