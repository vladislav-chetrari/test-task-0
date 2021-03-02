package com.example.testtask0.data.persistence

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesWrapper @Inject constructor(private val prefs: SharedPreferences) {

    var code: String
        get() = prefs.getString(CODE_KEY, "")!!
        set(value) = prefs.edit {
            putString(CODE_KEY, value)
        }

    private companion object {
        const val CODE_KEY = "code"
    }
}