package com.example.wallpaper

import com.example.wallpaper.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        plantTimber()
    }

    private fun plantTimber() {
        Timber.plant(Timber.DebugTree())
    }
}