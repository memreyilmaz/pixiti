package com.example.pixiti.di

import com.example.pixiti.data.ImageRepository
import com.example.pixiti.data.PixabayApi
import org.koin.dsl.module

val repositoryModule = module {
    single { provideImageRepository(get()) }
}

private fun provideImageRepository(api: PixabayApi) = ImageRepository(api)
