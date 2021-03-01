package com.example.testtask0.di.module

import android.content.Context
import android.content.res.Resources
import com.example.testtask0.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    internal fun context(app: App): Context = app.applicationContext

    @Provides
    internal fun resources(context: Context): Resources = context.resources
}