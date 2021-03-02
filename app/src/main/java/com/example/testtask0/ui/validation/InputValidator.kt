package com.example.testtask0.ui.validation

import android.content.res.Resources
import androidx.annotation.StringRes
import com.example.testtask0.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InputValidator @Inject constructor(private val resources: Resources) {

    fun validateInputBy(input: String, rules: List<Rule>): String? {
        val computedError = rules.mapNotNull { it.validate(input) }
            .joinToString(separator = "\n") { it.message(resources) }
        return if (computedError.isNotBlank()) computedError else null
    }

    sealed class Rule {
        object NotEmpty : Rule() {
            override fun validate(input: String) = if (input.isBlank()) Violation.FieldRequired else null
        }

        internal abstract fun validate(input: String): Violation?
    }

    sealed class Violation(@StringRes private val stringResId: Int) {
        object FieldRequired : Violation(R.string.error_field_required)

        internal open fun message(resources: Resources) = resources.getString(stringResId)
    }
}