package com.example.pixiti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixiti.data.ImageRepository
import com.example.pixiti.model.ImageResponse
import kotlinx.coroutines.launch

class ImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {

   private val _imagesList = MutableLiveData<ImageResponse>()
   val imagesList: LiveData<ImageResponse>
        get() = _imagesList

    fun getImagesList(query: String?) {
        viewModelScope.launch {
            _imagesList.value = query?.let {
                imageRepository.getImages(it)
            } ?: run {
                imageRepository.getImages(DEFAULT_SEARCH_QUERY)
            }
        }
    }
}