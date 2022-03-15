package com.github.husseinhj.githubuser.modules

import org.koin.dsl.module
import com.github.husseinhj.githubuser.services.repositories.UserRepository
import com.github.husseinhj.githubuser.services.repositories.SearchRepository

val repositoryModules = module {
    single {
        UserRepository(get())
    }

    single {
        SearchRepository(get())
    }
}