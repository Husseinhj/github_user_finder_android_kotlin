package com.github.husseinhj.githubuser.views.activities

import android.os.Bundle
import android.view.Menu
import com.github.husseinhj.githubuser.R
import org.koin.android.ext.android.inject
import androidx.databinding.DataBindingUtil
import org.koin.core.parameter.parametersOf
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.github.husseinhj.githubuser.utils.ToolbarAppearance
import com.github.husseinhj.githubuser.databinding.ActivityMainBinding
import com.github.husseinhj.githubuser.viewmodels.activities.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    val toolbarAppearance: ToolbarAppearance by inject {
        parametersOf(this, R.menu.main_menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = mainActivityViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        toolbarAppearance.configureSearchBarByMenu(menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    override fun onBackPressed() {
        if (!toolbarAppearance.collapseSearchBar()) {
            super.onBackPressed()
        }
    }
}