package com.example.testtask0

import android.app.Application
import com.example.testtask0.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var timberTree: Timber.Tree

    override fun androidInjector() = injector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        Timber.plant(timberTree)
    }
}