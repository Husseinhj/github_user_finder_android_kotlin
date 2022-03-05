package com.github.husseinhj.githubuser.services.interceptors

import okhttp3.Response
import okhttp3.Interceptor

class AuthenticationInterceptor : Interceptor {

    /*
    The interceptor shows that we can handle authentication parameters before sending to the server and after receiving a response to handle refresh_token.
    As a sample, I commented access_token in code to show where to add our token.
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