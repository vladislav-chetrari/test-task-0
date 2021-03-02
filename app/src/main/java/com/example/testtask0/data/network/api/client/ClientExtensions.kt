package com.example.testtask0.data.network.api.client

import retrofit2.Call

internal val <T> Call<T>.body: T
    get() {
        val response = execute()
        when {
            response.isSuccessful -> return response.body()!!
            else -> throw Throwable(response.errorBody()?.string())
        }
    }