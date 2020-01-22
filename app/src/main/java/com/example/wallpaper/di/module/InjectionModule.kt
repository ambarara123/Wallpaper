package com.example.wallpaper.di.module

import com.example.wallpaper.data.MainRepository
import com.example.wallpaper.ui.detail.DetailViewModel
import com.example.wallpaper.ui.main.MainViewModel
import com.example.wallpaper.ui.rx.RxViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { AppModule.providesOkHttp() }

    single { AppModule.provideGsonConverterFactory() }

    single { AppModule.provideRetrofit(get(),get()) }

    single { AppModule.provideNetworkService(get()) }
}

val managerModule = module {
    single { AppModule.provideDownloadManagerUtils(get()) }

    single { AppModule.provideWallpaperManager(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }

    viewModel { DetailViewModel(get(),get()) }

    viewModel { RxViewModel() }
}

val repositoryModule = module {
    single { MainRepository(get()) }
}

