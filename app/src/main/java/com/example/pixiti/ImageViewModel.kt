package com.example.pixiti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pixiti.data.ImageRepository
import com.example.pixiti.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    private var currentQuery: String? = null
    private var currentSearchResult: Flow<PagingData<Image>>? = null
    private var _randomImage = MutableLiveData<Image>()

    val randomImage : LiveData<Image>
        get() = _randomImage

    init {
        getRandomImage()
    }

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

    private fun getRandomImage() {
        viewModelScope.launch {
            _randomImage.value =
                imageRepository.getRandomImage()
        }
    }
}