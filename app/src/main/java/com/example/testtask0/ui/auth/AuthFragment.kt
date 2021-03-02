package com.example.testtask0.ui.auth

import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
import com.example.testtask0.R
import com.example.testtask0.ui.auth.AuthFragmentDirections.Companion.actionAuthFragmentToLocationsFragment
import com.example.testtask0.ui.base.BaseFragment
import com.example.testtask0.ui.extension.onTextChange
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    private val viewModel by viewModels<AuthViewModel>()

    private val authButton: ExtendedFloatingActionButton
        get() = view(R.id.authButton)
    private val usernameTextLayout: TextInputLayout
        get() = view(R.id.usernameInputLayout)
    private val usernameTextField: TextInputEditText
        get() = view(R.id.usernameInputField)
    private val passwordTextLayout: TextInputLayout
        get() = view(R.id.passwordInputLayout)
    private val passwordTextField: TextInputEditText
        get() = view(R.id.passwordInputField)
    private val coordinator: CoordinatorLayout
        get() = view(R.id.coordinator)
    private val progressSnackbar by lazy { Snackbar.make(coordinator, R.string.progress, LENGTH_INDEFINITE) }
    private val errorSnackbar by lazy { Snackbar.make(coordinator, R.string.error_unknown, LENGTH_INDEFINITE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernameTextField.onTextChange(viewModel::onUsernameChanged)
        passwordTextField.onTextChange(viewModel::onPasswordChanged)
        authButton.setOnClickListener { viewModel.onSignIn() }
    }

    override fun observeLiveData() = viewModel.run {
        usernameInputError.observe { usernameTextLayout.error = it }
        passwordInputError.observe { passwordTextLayout.error = it }
        authButtonEnabled.observe { authButton.isEnabled = it }
        authResult.observeResult(
            onProgress = ::showProgress,
            onComplete = { progressSnackbar.dismiss() },
            onError = ::showError,
            onSuccess = { proceed() }
        )
    }

    private fun showProgress() = authButton.hide(object : ExtendedFloatingActionButton.OnChangedCallback() {
        override fun onHidden(extendedFab: ExtendedFloatingActionButton?) = progressSnackbar.show()
    })

    private fun showError(error: Throwable) = errorSnackbar.run {
        setText(error.message ?: getString(R.string.error_unknown))
        setAction(android.R.string.ok) {
            dismiss()
            authButton.show()
        }
        show()
    }

    private fun proceed() {
        findNavController().navigate(actionAuthFragmentToLocationsFragment())
    }
}