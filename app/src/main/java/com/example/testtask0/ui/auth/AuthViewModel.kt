package com.example.testtask0.ui.auth

import androidx.lifecycle.*
import com.example.testtask0.data.network.api.client.AuthClient
import com.example.testtask0.data.network.api.request.AuthRequest
import com.example.testtask0.data.persistence.SharedPreferencesWrapper
import com.example.testtask0.ui.Result
import com.example.testtask0.ui.base.BaseViewModel
import com.example.testtask0.ui.validation.InputValidator
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authClient: AuthClient,
    private val preferencesWrapper: SharedPreferencesWrapper,
    private val inputValidator: InputValidator
) : BaseViewModel() {

    private val compositeInput = MutableLiveData<HashMap<String, String>>()
    private val compositeInputError = compositeInput.map { inputMap ->
        inputMap.mapValues { inputValidator.validateInputBy(it.value, validationRules[it.key] ?: emptyList()) }
    }
    val authButtonEnabled = compositeInputError.map { it.hasOnlyNullsOrEmpty() }.distinctUntilChanged()
    val usernameInputError: LiveData<String?> = compositeInputError.map { it[USERNAME] }.distinctUntilChanged()
    val passwordInputError: LiveData<String?> = compositeInputError.map { it[PASSWORD] }.distinctUntilChanged()

    private val authTrigger = MutableLiveData<AuthRequest>()
    val authResult = authTrigger.switchMap { resultLiveData(Dispatchers.IO) { authClient.authenticate(it) } }
        .mapResult { preferencesWrapper.code = it.code }

    init {
        val code = preferencesWrapper.code
        if (code.isNotBlank()) {
            authResult.mediator.postValue(Result.Complete.Success(Unit))
        } else {
            compositeInput.postValue(hashMapOf(USERNAME to "", PASSWORD to ""))
        }
    }

    fun onUsernameChanged(username: String) = registerInput(USERNAME, username)

    fun onPasswordChanged(password: String) = registerInput(PASSWORD, password)

    private fun registerInput(key: String, value: String) {
        val map = compositeInput.value ?: hashMapOf()
        map[key] = value
        compositeInput.value = map
    }

    fun onSignIn() = authTrigger.postValue(
        AuthRequest(
            compositeInput.value?.get(USERNAME) ?: "",
            compositeInput.value?.get(PASSWORD) ?: ""
        )
    )

    private fun Map<*, *>.hasOnlyNullsOrEmpty() = values.filterNotNull().isEmpty()

    private companion object {
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        val validationRules: Map<String, List<InputValidator.Rule>> = mapOf(
            USERNAME to listOf(InputValidator.Rule.NotEmpty),
            PASSWORD to listOf(InputValidator.Rule.NotEmpty)
        )
    }
}