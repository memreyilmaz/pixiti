package com.example.pixiti.data

import com.example.pixiti.model.ImageResponse

interface ImageRepository {

    suspend fun getImages(query: String): ImageResponse
}