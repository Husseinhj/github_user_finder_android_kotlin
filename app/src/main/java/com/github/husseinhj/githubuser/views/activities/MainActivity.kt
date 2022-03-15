package com.github.husseinhj.githubuser.views.activities

import android.os.Bundle
import com.github.husseinhj.githubuser.R
import androidx.databinding.DataBindingUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.github.husseinhj.githubuser.bases.BaseAppCompatActivity
import com.github.husseinhj.githubuser.databinding.ActivityMainBinding
import com.github.husseinhj.githubuser.viewmodels.activities.MainActivityViewModel

class MainActivity : BaseAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = mainActivityViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }
}