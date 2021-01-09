package com.example.pixiti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pixiti.data.ImageRepository
import com.example.pixiti.model.Image
import kotlinx.coroutines.flow.Flow

class ImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    private var currentQuery: String? = null
    private var currentSearchResult: Flow<PagingData<Image>>? = null

    fun searchImages(query: String): Flow<PagingData<Image>> {
        val lastResult = currentSearchResult
        if (query == currentQuery && lastResult != null) {
            return lastResult
        }
        currentQuery = query
        val newResult: Flow<PagingData<Image>> = imageRepository.getImages(query)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}