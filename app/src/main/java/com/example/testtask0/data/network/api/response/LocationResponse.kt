package com.example.testtask0.data.network.api.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    val id: String = "",
    val name: String = "",
    val country: String = "",
    @SerializedName("lat")
    val latitude: Double = .0,
    @SerializedName("lon")
    val longitude: Double = .0,
)
