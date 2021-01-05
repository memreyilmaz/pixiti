package com.example.pixiti.di

import com.example.pixiti.data.PixabayApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): PixabayApi {
        return retrofit.create(PixabayApi::class.java)
    }

    single { provideUserApi(get()) }
}
