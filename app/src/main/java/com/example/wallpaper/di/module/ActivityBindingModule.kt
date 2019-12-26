package com.example.wallpaper.di.module

import androidx.appcompat.app.AppCompatActivity
import com.example.wallpaper.ui.detail.DetailActivity
import com.example.wallpaper.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindDetailActivity() : DetailActivity

}