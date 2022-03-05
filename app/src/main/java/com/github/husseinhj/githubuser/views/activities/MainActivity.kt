package com.github.husseinhj.githubuser.views.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.databinding.ActivityMainBinding
import com.github.husseinhj.githubuser.extensions.debounce
import com.github.husseinhj.githubuser.models.eventbus.OnBackButtonVisibilityMessage
import com.github.husseinhj.githubuser.models.eventbus.OnSearchBarMessage
import com.github.husseinhj.githubuser.viewmodels.activities.MainActivityViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = MainActivityViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        EventBus.getDefault().register(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.search_button)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextFocusChangeListener { _, focused ->
            EventBus.getDefault().post(OnSearchBarMessage(focused))
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            @SuppressLint("CheckResult")
            override fun onQueryTextChange(newText: String?): Boolean {
                newText.debounce {
                    EventBus.getDefault().post(OnSearchBarMessage(query = it))
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.onActionViewCollapsed()
        } else {
            super.onBackPressed()
        }
    }

    @Subscribe
    fun onBackButtonVisibilityMessage(message: OnBackButtonVisibilityMessage) {
        supportActionBar?.setDisplayHomeAsUpEnabled(message.visibleBackButton)
    }

    @Subscribe
    fun onSearchBarMessage(message: OnSearchBarMessage) {
        if (message.focused == false) {
            searchView.clearFocus()
        }
    }
}