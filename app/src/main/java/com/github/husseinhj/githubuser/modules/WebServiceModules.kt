package com.github.husseinhj.githubuser.modules

import com.github.husseinhj.githubuser.services.repositories.SearchRepository
import com.github.husseinhj.githubuser.services.repositories.UserRepository
import retrofit2.Retrofit
import org.koin.dsl.module
import com.github.husseinhj.githubuser.services.repositories.interfaces.IUserRepository
import com.github.husseinhj.githubuser.services.repositories.interfaces.ISearchRepository

val webServiceModules = module {
    single(createdAtStart = false) { get<Retrofit>().create(IUserRepository::class.java) }
    single(createdAtStart = false) { get<Retrofit>().create(ISearchRepository::class.java) }
    single { UserRepository(get()) }
    single { SearchRepository(get()) }
}