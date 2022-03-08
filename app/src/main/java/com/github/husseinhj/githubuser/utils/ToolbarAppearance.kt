package com.github.husseinhj.githubuser.utils

import android.view.Menu
import android.view.MenuItem
import android.content.Context
import android.app.SearchManager
import android.annotation.SuppressLint
import com.github.husseinhj.githubuser.R
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.github.husseinhj.githubuser.extensions.showSoftBackButton

typealias OnTextChangedListener = (String?) -> Unit
typealias OnFocusChangedListener = (Boolean) -> Unit

/**
 * A wrapper class that handles toolbar appearance, states, and events.
 *
 * ## Initialize
 * To initialize, it requires passing **AppCompatActivity** and **Menu** resource ids
 * to the constructor, as shown on the following snippet of code:
 *
 * ``` kotlin
 *
 * val toolbarAppearance = ToolbarAppearance(this, R.menu.main_menu)
 *
 * ```
 *
 * ## Config
 * To configure the search bar, call the method [configureSearchBarByMenu]
 * in the launcher Activity class, in the **onCreateOptionsMenu** method as follows:
 *
 * ```
 *
 * override fun onCreateOptionsMenu(menu: Menu): Boolean {
 *     this.toolbarAppearance.configureEnableSearchBar(menu)
 *
 *     return super.onCreateOptionsMenu(menu)
 * }
 *```
 *
 *
 * <p>
 * More information about SearchView widget:
 * @see androidx.appcompat.widget.SearchView
 * </p>
 *
 */
class ToolbarAppearance(private val activity: AppCompatActivity, private val menuResId: Int) {

    private var searchView: SearchView? = null
    private var focusListener: OnFocusChangedListener? = null
    private var textChangedListener: OnTextChangedListener? = null

    fun setShowBackSoftButton(show: Boolean) {
        activity.showSoftBackButton(show)
    }

    fun setTitle(title: String?) {
        activity.supportActionBar?.title = title
    }

    fun setOnFocusListener(listener: OnFocusChangedListener?) {
        this.focusListener = listener
    }

    fun setOnTextChangedListener(listener: OnTextChangedListener?) {
        this.textChangedListener = listener
    }

    fun collapseSearchBar(): Boolean {
        if (searchView?.isIconified == false) {
            /*
            * After collapsing the SearchView, the **onActionViewCollapsed** method
            * would clear the search input and call the **onQueryTextChange** callback.
            * Therefore, we must clear the **setOnQueryTextListener** and
            * add listeners after collapsing the SearchView.
            * */
            searchView?.setOnQueryTextListener(null)
            searchView?.onActionViewCollapsed()
            searchView?.setOnQueryTextListener(getOnTextChangeListenerImp())

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

    fun configureSearchBarByMenu(menu: Menu) {
        activity.menuInflater.inflate(menuResId, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.search_button)
        val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        searchView?.setOnQueryTextFocusChangeListener { _, focused ->
            try {
                this.focusListener?.invoke(focused)
            } catch (e: Exception) {
                println("Exception happen during call OnFocusChangedListener with ${e.message}")
            }
        }

        searchView?.setOnQueryTextListener(getOnTextChangeListenerImp())
    }

    private fun getOnTextChangeListenerImp(): SearchView.OnQueryTextListener {
        return object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                this@ToolbarAppearance.clearFocusOnSearchBar()
                return true
            }

            @SuppressLint("CheckResult")
            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    this@ToolbarAppearance.textChangedListener?.invoke(newText)
                } catch (e: Exception) {
                    println("Exception happen during call OnTextChangedListener with ${e.message}")
                }
                return true
            }
        }
    }
}