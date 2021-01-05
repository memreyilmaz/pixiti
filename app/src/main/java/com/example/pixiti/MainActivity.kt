package com.example.pixiti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.lifecycle.*

class MainActivity : AppCompatActivity() {

    private val imageViewModel by viewModel<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageViewModel.getImagesList("fruit")
        imageViewModel.imagesList.observe(this, {
            if (!it.images.isNullOrEmpty()) {
            }
        })
    }
}