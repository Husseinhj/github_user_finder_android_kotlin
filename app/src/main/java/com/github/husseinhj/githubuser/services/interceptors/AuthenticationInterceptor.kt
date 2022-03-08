package com.github.husseinhj.githubuser.services.interceptors

import okhttp3.Response
import okhttp3.Protocol
import okhttp3.Interceptor
import okhttp3.ResponseBody.Companion.toResponseBody

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

        return try {
            chain.proceed(request)
        } catch (e: Exception) {
            e.printStackTrace()

            Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message(e.message ?: "Unknown error happen.")
                .body(e.cause.toString().toResponseBody(null))
                .build()
        }
    }

}