package com.example.pixiti.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pixiti.model.Image
import com.example.pixiti.paging.ImagePagingSource
import kotlinx.coroutines.flow.Flow

class ImageRepository(private val pixabayApi: PixabayApi)  {

    fun getImages(query: String) : Flow<PagingData<Image>> {
        return Pager(PagingConfig(IMAGE_PAGE_SIZE, IMAGE_MAX_SIZE, false)) {
            ImagePagingSource(pixabayApi, query)
        }.flow
    }

    suspend fun getRandomImage() : Image? {
        return pixabayApi.getOneImage().images.randomOrNull()
    }

    companion object {
        private const val IMAGE_PAGE_SIZE = 20
        private const val IMAGE_MAX_SIZE = 100
    }
}