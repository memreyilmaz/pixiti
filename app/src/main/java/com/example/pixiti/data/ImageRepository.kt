package com.example.pixiti.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pixiti.model.Image
import com.example.pixiti.paging.ImagePagingSource
import kotlinx.coroutines.flow.Flow

class ImageRepository(private val api: PixabayApi)  {

    fun getImages(query: String) : Flow<PagingData<Image>> {
        return Pager(PagingConfig(IMAGE_PAGE_SIZE)) {
            ImagePagingSource(api, query)
        }.flow
    }

    companion object {
        private const val IMAGE_PAGE_SIZE = 20
    }
}