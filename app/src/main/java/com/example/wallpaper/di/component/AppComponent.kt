package com.example.wallpaper.di.component

import com.example.wallpaper.MyApplication
import com.example.wallpaper.di.module.ActivityBindingModule
import com.example.wallpaper.di.module.AppModule
import com.example.wallpaper.di.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [AppModule::class,ViewModelModule::class, AndroidInjectionModule::class,ActivityBindingModule::class])
@Singleton
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<MyApplication>
}