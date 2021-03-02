package com.example.testtask0.ui.extension

import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.findViewTreeLifecycleOwner
import timber.log.Timber

fun TextView.onTextChange(listener: (String) -> Unit) {
    val tw = addTextChangedListener { listener("$it") }
    findViewTreeLifecycleOwner()?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            Timber.d("textWatcher destroyed")
            removeTextChangedListener(tw)
        }
    })
}