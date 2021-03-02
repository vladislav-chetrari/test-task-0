package com.example.testtask0.ui

sealed class Result<out T> {
    object Progress : Result<Nothing>()
    sealed class Complete<T> : Result<T>() {
        class Success<T>(val value: T) : Result<T>()
        class Error(val error: Throwable) : Result<Nothing>()
    }
}