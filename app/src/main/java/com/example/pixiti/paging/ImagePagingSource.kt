package com.example.pixiti.paging

import androidx.paging.PagingSource
import com.example.pixiti.data.PixabayApi
import com.example.pixiti.model.Image
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(
    private val pixabayApi: PixabayApi,
    private val query: String
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val imageListResponse = pixabayApi.searchImages(query, position, params.loadSize)

            LoadResult.Page(
                data = imageListResponse.images,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (imageListResponse.images.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e : HttpException){
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}