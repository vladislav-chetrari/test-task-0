package com.example.testtask0.di.module

import android.content.res.Resources
import com.example.testtask0.BuildConfig
import com.example.testtask0.R
import com.example.testtask0.data.network.api.Api
import com.example.testtask0.data.network.api.interceptor.AuthTokenInterceptor
import com.example.testtask0.di.BaseUrl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun okHttpClient(
        authTokenInterceptor: AuthTokenInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(authTokenInterceptor)
        .apply { if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor) }
        .build()

    @Provides
    @Singleton
    @BaseUrl
    fun baseUrl(res: Resources): String = res.getString(R.string.base_url)

    @Provides
    @Singleton
    fun retrofit(@BaseUrl baseUrl: String, client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun api(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    private companion object {
        const val REQUEST_TIMEOUT = 90L
    }
}