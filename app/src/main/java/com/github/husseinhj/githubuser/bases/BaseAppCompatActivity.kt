package com.github.husseinhj.githubuser.bases

import android.view.Menu
import com.github.husseinhj.githubuser.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import androidx.appcompat.app.AppCompatActivity
import com.github.husseinhj.githubuser.utils.ToolbarAppearance

open class BaseAppCompatActivity: AppCompatActivity() {
    private val toolbarAppearance: ToolbarAppearance by inject {
        parametersOf(this, R.menu.main_menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        toolbarAppearance.configureSearchBarByMenu(menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (!toolbarAppearance.collapseSearchBar()) {
            super.onBackPressed()
        }
    }
}