package com.example.pixiti.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("totalHits")
    val totalHits: Int,
    @SerializedName("hits")
    val images: List<Image>,
    @SerializedName("total")
    val total: Int
)