package com.example.testtask0.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import javax.inject.Scope
import kotlin.reflect.KClass

@Scope
@Retention
annotation class ActivityScoped

@Scope
@Retention
annotation class FragmentScoped

@Qualifier
annotation class BaseUrl

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
