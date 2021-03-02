package com.example.testtask0.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val id: String = "",
    val name: String = "",
    val country: String = "",
    val imageUrl: String = "",
    val latitude: Double = .0,
    val longitude: Double = .0,
) : Parcelable