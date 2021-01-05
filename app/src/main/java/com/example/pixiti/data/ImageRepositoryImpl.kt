package com.example.pixiti.data

import com.example.pixiti.model.ImageResponse

class ImageRepositoryImpl(private val api: PixabayApi) : ImageRepository {

    override suspend fun getImages(query: String): ImageResponse {
        return api.getImagesList(query, 1, 10)
    }
}