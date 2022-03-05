package com.github.husseinhj.githubuser.services.providers

import com.github.husseinhj.githubuser.consts.GITHUB_API_BASE_URL
import com.github.husseinhj.githubuser.services.interceptors.AuthenticationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
This a provider for build our network client and make it transparent for out side to not knowing about which service we are using.
 */
object WebServiceProvider {

    private fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthenticationInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <S> createService(serviceClass: Class<S>?): S {
        return getRetrofit().create(serviceClass!!)
    }

}