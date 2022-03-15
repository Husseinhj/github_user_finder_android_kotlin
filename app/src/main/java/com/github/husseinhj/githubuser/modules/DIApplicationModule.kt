package com.github.husseinhj.githubuser.modules

import org.koin.dsl.module
import com.github.husseinhj.githubuser.utils.ToolbarAppearance

val applicationModule = module {
    // Toolbar
    single {
        ToolbarAppearance(get(), get())
    }
}