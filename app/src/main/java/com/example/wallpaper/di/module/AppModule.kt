package com.example.wallpaper.di.module

import android.app.WallpaperManager
import android.content.Context
import com.example.wallpaper.MyApplication
import com.example.wallpaper.network.NetworkService
import com.example.wallpaper.utils.BASE_URL
import com.example.wallpaper.utils.DownloadMangerUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: MyApplication): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesOkhttp(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideGsonConvertorFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideWallpaperManager(context: Context): WallpaperManager {
        return WallpaperManager.getInstance(context)
    }

   /* @Provides
    @Singleton
    fun provideDownloadManagerUtil(context: Context): DownloadMangerUtil {
        return DownloadMangerUtil(context)
    }*/

}