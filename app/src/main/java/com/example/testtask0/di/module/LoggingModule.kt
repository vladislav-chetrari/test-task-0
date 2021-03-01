package com.example.testtask0.di.module

import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
class LoggingModule {

    @Provides
    @Singleton
    fun timberTree(): Timber.Tree = Timber.DebugTree()

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}