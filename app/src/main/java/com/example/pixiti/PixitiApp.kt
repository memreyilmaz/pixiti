package com.example.pixiti

import android.app.Application
import com.example.pixiti.di.apiModule
import com.example.pixiti.di.netModule
import com.example.pixiti.di.repositoryModule
import com.example.pixiti.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PixitiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        //TODO temp
        Timber.plant()
    }

    private fun initKoin() {
        startKoin {
            // androidLogger(Level.ERROR)
            androidContext(this@PixitiApp)
            modules(listOf(viewModelModule, repositoryModule, netModule, apiModule))
        }
    }
}