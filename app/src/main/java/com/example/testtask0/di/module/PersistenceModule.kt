package com.example.testtask0.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun sharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)

    private companion object {
        const val SHARED_PREFS_NAME = "kk"
    }
}