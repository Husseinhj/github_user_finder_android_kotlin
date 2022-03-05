package com.github.husseinhj.githubuser.services.interceptors

import okhttp3.Response
import okhttp3.Interceptor

class AuthenticationInterceptor : Interceptor {

    /*
    Interceptor add to shows that we can handle authentication parameters before send to server or after getting response to handle refresh_token.
    As sample added commented access_token in code to shows that were we most add our token.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val authenticatedUrl = original.url.newBuilder().build()
            //.addQueryParameter("access_token", "{AUTHENTICTION_KEY}").build()

        val requestBuilder = original.newBuilder().url(authenticatedUrl)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }

}