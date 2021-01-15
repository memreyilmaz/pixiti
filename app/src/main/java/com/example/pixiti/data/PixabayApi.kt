package com.example.pixiti.data

import com.example.pixiti.KEY
import com.example.pixiti.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET(KEY)
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ImageResponse

    @GET(KEY)
    suspend fun getOneImage(
        @Query("image_type") imageType: String? = "photo",
        @Query("orientation") orientation: String? = "vertical"): ImageResponse
}