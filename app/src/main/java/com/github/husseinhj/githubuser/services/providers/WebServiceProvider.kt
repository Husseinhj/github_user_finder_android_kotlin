package com.github.husseinhj.githubuser.services.providers

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import org.koin.core.scope.Scope
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import com.github.husseinhj.githubuser.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory
import com.github.husseinhj.githubuser.consts.READ_TIMEOUT
import com.github.husseinhj.githubuser.consts.WRITE_TIMEOUT
import com.github.husseinhj.githubuser.consts.CONNECT_TIMEOUT
import com.github.husseinhj.githubuser.consts.GITHUB_API_BASE_URL
import com.github.husseinhj.githubuser.services.interceptors.NetworkInterceptor
import com.github.husseinhj.githubuser.services.interceptors.AuthenticationInterceptor

fun Scope.getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(GITHUB_API_BASE_URL)
        .client(get())
        .addConverterFactory(GsonConverterFactory.create(get()))
        .build()
}

fun retrofitHttpClient(
    networkInterceptor: NetworkInterceptor,
    authenticationInterceptor: AuthenticationInterceptor,
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

        .addInterceptor(authenticationInterceptor)
        .addNetworkInterceptor(networkInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.HEADERS
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()
}