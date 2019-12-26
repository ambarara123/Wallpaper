package com.example.wallpaper.di.module

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import com.example.wallpaper.MyApplication
import com.example.wallpaper.network.NetworkService
import com.example.wallpaper.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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
    fun providesOkhttp():OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory):Retrofit{
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
    fun provideGsonConvertorFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideDownloadManager(context: Context) : DownloadManager{
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Provides
    @Singleton
    fun provideDestinationFile(context: Context): File {
        return File(context.getExternalFilesDir(null),"dummy.png")
    }

    @Provides
    @Singleton
    fun provideWallpaperManager(context: Context): WallpaperManager{
        return WallpaperManager.getInstance(context)
    }
}