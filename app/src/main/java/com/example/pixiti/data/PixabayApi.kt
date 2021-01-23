package com.example.pixiti.data

import com.example.pixiti.utils.KEY
import com.example.pixiti.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET(KEY)
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("image_type") imageType: String?,
        @Query("editors_choice") editorsChoice: Boolean?,
        @Query("orientation") orientation: String?,
        @Query("safesearch") safeSearch: Boolean?
    ): ImageResponse

    @GET(KEY)
    suspend fun getOneImage(
        @Query("image_type") imageType: String = "photo",
        @Query("safesearch") safeSearch: Boolean = true,
        @Query("editors_choice") editorsChoice: Boolean = true,
        @Query("orientation") orientation: String = "vertical"): ImageResponse
}