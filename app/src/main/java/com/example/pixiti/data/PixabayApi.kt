package com.example.pixiti.data

import com.example.pixiti.KEY
import com.example.pixiti.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET(KEY)
    suspend fun getImagesList(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ImageResponse
}