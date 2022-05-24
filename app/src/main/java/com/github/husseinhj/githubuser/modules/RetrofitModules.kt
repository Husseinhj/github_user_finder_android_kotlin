package com.github.husseinhj.githubuser.modules

import org.koin.dsl.module
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import com.github.husseinhj.githubuser.services.providers.getRetrofit
import com.github.husseinhj.githubuser.models.exceptions.NoInternetException
import com.github.husseinhj.githubuser.services.providers.retrofitHttpClient
import com.github.husseinhj.githubuser.services.interceptors.NetworkInterceptor
import com.github.husseinhj.githubuser.services.interceptors.AuthenticationInterceptor

val retrofitModule = module {
    single { getRetrofit() }
    single { GsonBuilder().create() }
    single { AuthenticationInterceptor() }
    single { retrofitHttpClient(get(), get()) }
    single { NetworkInterceptor(androidContext()) }
    factory { NoInternetException(androidContext()) }
}