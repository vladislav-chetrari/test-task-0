package com.example.testtask0.di.module

import com.example.testtask0.di.FragmentScoped
import com.example.testtask0.ui.auth.AuthFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun authFragment(): AuthFragment
}