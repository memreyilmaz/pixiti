package com.example.pixiti.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pixiti.model.Image
import com.example.pixiti.paging.ImagePagingSource
import kotlinx.coroutines.flow.Flow

class ImageRepository(private val pixabayApi: PixabayApi) {

    fun getImages(
        query: String,
        imageType: String?,
        editorsChoice: Boolean?,
        orientation: String?,
        safeSearch: Boolean?
    ): Flow<PagingData<Image>> {
        return Pager(PagingConfig(IMAGE_PAGE_SIZE, IMAGE_MAX_SIZE, false)) {
            ImagePagingSource(pixabayApi, query, imageType, editorsChoice, orientation, safeSearch)
        }.flow
    }

    suspend fun getRandomImage(): Image? {
        return pixabayApi.getOneImage().images.randomOrNull()
    }

    companion object {
        private const val IMAGE_PAGE_SIZE = 20
        private const val IMAGE_MAX_SIZE = 100
    }
}