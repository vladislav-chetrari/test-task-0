package com.example.testtask0.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask0.ui.Result
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment(
    @LayoutRes private val contentLayoutId: Int
) : Fragment(contentLayoutId), HasAndroidInjector {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = injector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    protected open fun observeLiveData() = Unit

    protected inline fun <reified VM : ViewModel> viewModels() = viewModels<VM> { factory }

    protected fun <T> LiveData<T>.observe(consumer: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer { consumer(it) })
    }

    protected fun <T> LiveData<T?>.safeObserve(consumer: (T) -> Unit) =
        observe(viewLifecycleOwner, Observer { it?.let(consumer) })

    protected fun <T : View?> view(@IdRes viewId: Int): T =
        requireView().findViewById<T>(viewId)

    protected fun <T> LiveData<Result<T>>.observeResult(
        onProgress: () -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onSuccess: (T) -> Unit = {}
    ) = observe { result ->
        when (result) {
            Result.Progress -> onProgress()
            is Result.Complete.Error -> {
                onComplete()
                onError(result.error)
            }
            is Result.Complete.Success -> {
                onComplete()
                onSuccess(result.value)
            }
        }
    }
}