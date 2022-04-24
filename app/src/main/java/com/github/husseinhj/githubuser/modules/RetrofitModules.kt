package com.github.husseinhj.githubuser.modules

import org.koin.dsl.module
import com.google.gson.GsonBuilder
import com.github.husseinhj.githubuser.services.providers.getRetrofit
import com.github.husseinhj.githubuser.services.providers.retrofitHttpClient
import com.github.husseinhj.githubuser.services.interceptors.AuthenticationInterceptor

val retrofitModule = module {
    single { getRetrofit() }
    single { retrofitHttpClient() }
    single { GsonBuilder().create() }
    single { AuthenticationInterceptor() }
}