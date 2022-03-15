package com.github.husseinhj.githubuser.modules

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.github.husseinhj.githubuser.viewmodels.fragments.HomeViewModel
import com.github.husseinhj.githubuser.viewmodels.fragments.SearchUserViewModel
import com.github.husseinhj.githubuser.viewmodels.fragments.UserDetailViewModel
import com.github.husseinhj.githubuser.viewmodels.activities.MainActivityViewModel

val viewModelModules = module {
    // Main Activity ViewModel
    viewModel {
        MainActivityViewModel()
    }

    // On-Boarding ViewModel
    viewModel {
        HomeViewModel()
    }

    // Search User ViewModel
    viewModel {
        SearchUserViewModel(get(), get())
    }

    // User Profile ViewModel
    viewModel {
        UserDetailViewModel(get(), get())
    }
}