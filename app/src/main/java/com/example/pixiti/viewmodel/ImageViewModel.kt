package com.example.pixiti.viewmodel

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

    private var imageType: String? = null
    private var editorsChoice: Boolean? = null
    private var orientation: String? = null
    private var safeSearch: Boolean? = null
    private var currentQuery: String? = null
    private var currentSearchResult: Flow<PagingData<Image>>? = null
    private var _randomImage = MutableLiveData<Image>()

    fun setImageType(imageType: String?) {
        this.imageType = imageType
    }

    fun setEditorsChoice(editorsChoice: Boolean?) {
        this.editorsChoice = editorsChoice
    }

    fun setOrientation(orientation: String?) {
        this.orientation = orientation
    }

    fun setSafeSearch(safeSearch: Boolean?) {
        this.safeSearch = safeSearch
    }

    val randomImage: LiveData<Image>
        get() = _randomImage

    init {
        getRandomImage()
    }

    fun searchImages(query: String): Flow<PagingData<Image>> {
        val lastResult = currentSearchResult
        if (query == currentQuery && lastResult != null && !isFilterApplied()) {
            return lastResult
        }
        currentQuery = query
        val newResult: Flow<PagingData<Image>> = imageRepository.getImages(
            query = query,
            imageType = imageType,
            editorsChoice = editorsChoice,
            orientation = orientation,
            safeSearch = safeSearch
        )
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        resetFilterParams()
        return newResult
    }

    private fun getRandomImage() {
        viewModelScope.launch {
            _randomImage.value =
                imageRepository.getRandomImage()
        }
    }

    private fun isFilterApplied(): Boolean {
        return !(imageType.isNullOrEmpty() && editorsChoice == null && orientation.isNullOrEmpty() && safeSearch == null)
    }

    private fun resetFilterParams() {
        imageType = null
        editorsChoice = null
        orientation = null
        safeSearch = null
    }
}