package com.example.testtask0.data.network.api

import com.example.testtask0.data.network.api.response.AuthResponse
import com.example.testtask0.data.network.api.response.LocationsPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("auth.cgi")
    fun auth(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<AuthResponse>

    @GET("data.cgi")
    fun locations(
        @Query("p") page: Int
    ): Call<LocationsPageResponse>
}