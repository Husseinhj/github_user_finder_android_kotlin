package com.github.husseinhj.githubuser.utils

import android.view.Menu
import android.view.MenuItem
import android.content.Context
import android.app.SearchManager
import java.lang.ref.WeakReference
import android.annotation.SuppressLint
import com.github.husseinhj.githubuser.R
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.github.husseinhj.githubuser.extensions.showSoftBackButton

typealias OnTextChangedListener = (String?) -> Unit
typealias OnFocusChangedListener = (Boolean) -> Unit

class ToolbarAppearance(private val activity: AppCompatActivity) {

    private var searchView: SearchView? = null
    private var focusListener: WeakReference<OnFocusChangedListener>? = null
    private var textChangedListener: WeakReference<OnTextChangedListener>? = null

    fun setShowBackSoftButton(show: Boolean) {
        activity.showSoftBackButton(show)
    }

    fun setTitle(title: String?) {
        activity.supportActionBar?.title = title
    }

    fun setOnFocusListener(listener: OnFocusChangedListener?) {
        this.focusListener = WeakReference(listener)
    }

    fun setOnTextChangedListener(listener: OnTextChangedListener?) {
        this.textChangedListener = WeakReference(listener)
    }

    fun collapseSearchBar(): Boolean {
        if (searchView?.isIconified == false) {
            searchView?.onActionViewCollapsed()
            return true
        }

        return false
    }

    fun expandSearchBar() {
        searchView?.onActionViewExpanded()
    }

    fun focusOnSearchBar() {
        searchView?.requestFocus()
    }

    fun clearFocusOnSearchBar() {
        searchView?.clearFocus()
    }

    fun configureEnableSearchBar(menu: Menu) {
        activity.menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.search_button)
        val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        searchView?.setOnQueryTextFocusChangeListener { _, focused ->
            try {
                this.focusListener?.get()?.invoke(focused)
            } catch (e: Exception) {
                println("Exception happen during call OnFocusChangedListener with ${e.message}")
            }
        }

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                this@ToolbarAppearance.clearFocusOnSearchBar()
                return true
            }

            @SuppressLint("CheckResult")
            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    this@ToolbarAppearance.textChangedListener?.get()?.invoke(newText)
                } catch (e: Exception) {
                    println("Exception happen during call OnTextChangedListener with ${e.message}")
                }
                return true
            }
        })
    }
}