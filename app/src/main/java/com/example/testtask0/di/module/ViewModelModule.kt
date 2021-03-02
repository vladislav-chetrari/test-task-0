package com.example.testtask0.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask0.di.ViewModelKey
import com.example.testtask0.ui.ViewModelFactory
import com.example.testtask0.ui.auth.AuthViewModel
import com.example.testtask0.ui.locations.LocationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun vmFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    internal abstract fun authViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    internal abstract fun locationsViewModel(viewModel: LocationsViewModel): ViewModel
}