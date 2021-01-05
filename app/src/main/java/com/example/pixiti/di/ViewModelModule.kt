package com.example.pixiti.di

import com.example.pixiti.ImageViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ImageViewModel(get()) }
}