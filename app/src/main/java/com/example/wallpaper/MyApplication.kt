package com.example.wallpaper

import android.app.Application
import com.example.wallpaper.di.module.managerModule
import com.example.wallpaper.di.module.networkModule
import com.example.wallpaper.di.module.repositoryModule
import com.example.wallpaper.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.Koin
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()

            modules(listOf(networkModule, managerModule, viewModelModule, repositoryModule))
        }
        Timber.plant(Timber.DebugTree())
    }
}