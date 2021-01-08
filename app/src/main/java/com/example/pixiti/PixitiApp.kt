package com.example.pixiti

import android.app.Application
import com.example.pixiti.di.apiModule
import com.example.pixiti.di.netModule
import com.example.pixiti.di.repositoryModule
import com.example.pixiti.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PixitiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@PixitiApp)
            modules(listOf(viewModelModule, repositoryModule, netModule, apiModule))
        }
    }
}