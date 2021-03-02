package com.example.testtask0.ui.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(imageUrl: String) {
    Glide.with(context).load(imageUrl).centerCrop().into(this)
}