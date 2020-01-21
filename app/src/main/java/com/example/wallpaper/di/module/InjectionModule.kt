package com.example.wallpaper.di.module

import org.koin.dsl.module

val networkModule = module {
    single { AppModule.providesOkHttp() }

    single { AppModule.provideGsonConverterFactory() }

    single { AppModule.provideRetrofit(get(),get()) }

    single { AppModule.provideNetworkService(get()) }
}