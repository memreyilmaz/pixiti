package com.example.pixiti.paging

import androidx.paging.PagingSource
import com.example.pixiti.data.PixabayApi
import com.example.pixiti.model.Image

class ImagePagingSource(
    private val api: PixabayApi,
    private val query: String
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {

        return try {
            val nextPage = params.key ?: 1
            val imageListResponse = api.getImagesList(query, nextPage, params.loadSize)

            LoadResult.Page(
                data = imageListResponse.images,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}