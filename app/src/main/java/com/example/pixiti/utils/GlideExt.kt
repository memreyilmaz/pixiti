package com.example.pixiti.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pixiti.R

fun ImageView.loadImage(imageUrl: String?, context: Context) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.pixabay_logo)
        .error(R.drawable.pixabay_logo)
        .into(this)
}

fun ImageView.loadImage(resource: Int?, context: Context) {
    Glide.with(context)
        .load(resource)
        .placeholder(R.drawable.pixabay_logo)
        .error(R.drawable.pixabay_logo)
        .into(this)
}