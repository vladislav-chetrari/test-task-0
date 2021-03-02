package com.example.testtask0.data.network.api.interceptor

import com.example.testtask0.data.persistence.SharedPreferencesWrapper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenInterceptor @Inject constructor(
    private val preferencesWrapper: SharedPreferencesWrapper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url
        val isAuth = url.pathSegments.any { it.contains("auth") }
        if (isAuth.not()) {
            request = request.newBuilder().url(
                url.newBuilder()
                    .addQueryParameter("code", preferencesWrapper.code)
                    .build()
            ).build()
        }
        return chain.proceed(request)
    }
}