package com.example.testtask0.data.network.api.client

import android.content.res.Resources
import com.example.testtask0.R
import com.example.testtask0.data.network.api.Api
import com.example.testtask0.data.network.api.request.AuthRequest
import com.example.testtask0.data.network.api.response.AuthResponse
import com.example.testtask0.data.network.exception.WrongCredentialsException
import javax.inject.Inject

class AuthClient @Inject constructor(
    private val api: Api,
    private val resources: Resources
) {

    fun authenticate(request: AuthRequest): AuthResponse {
        val response = api.auth(request.username, request.password).body
        if (response.status == "error") {
            throw WrongCredentialsException(resources.getString(R.string.error_wrong_credentials))
        } else if (response.status != "ok") {
            throw Exception(resources.getString(R.string.error_unknown))
        }
        return response
    }
}