package com.example.wallpaper.di.module

import com.example.wallpaper.ui.detail.DetailActivity
import com.example.wallpaper.ui.main.MainActivity
import com.example.wallpaper.ui.rx.TestRXActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun bindTestRxActivity(): TestRXActivity

}