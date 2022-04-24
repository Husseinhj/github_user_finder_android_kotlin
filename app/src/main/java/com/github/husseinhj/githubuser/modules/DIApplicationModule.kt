package com.github.husseinhj.githubuser.modules

import org.koin.dsl.module
import com.github.husseinhj.githubuser.utils.ToolbarAppearance
import com.github.husseinhj.githubuser.utils.NavigationAppearance

val applicationModule = module {
    // Toolbar
    single {
        ToolbarAppearance(get(), get())
    }

    // Navigation
    factory {
        NavigationAppearance(get())
    }
}