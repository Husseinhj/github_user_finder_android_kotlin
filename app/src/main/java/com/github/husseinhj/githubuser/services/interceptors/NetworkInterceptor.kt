package com.github.husseinhj.githubuser.services.interceptors

import okhttp3.Response
import okhttp3.Interceptor
import android.content.Context
import com.github.husseinhj.githubuser.utils.InternetConnectivityUtil
import com.github.husseinhj.githubuser.models.exceptions.NoInternetException

class NetworkInterceptor(
    val context: Context
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!InternetConnectivityUtil.isInternetAvailable(context)) {
            throw NoInternetException(context)
        }

        return chain.proceed(chain.request())
    }
}