package com.example.testtask0.data.network.api.response

import com.google.gson.annotations.SerializedName

data class LocationsPageResponse(
    val status: String = "",
    val page: String = "",
    @SerializedName("data")
    val locations: List<LocationResponse> = emptyList()
)
