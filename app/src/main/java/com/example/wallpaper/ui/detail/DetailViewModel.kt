package com.example.wallpaper.ui.detail

import android.app.DownloadManager
import android.app.WallpaperManager
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.ui.base.BaseViewModel
import com.example.wallpaper.utils.downloadImage
import kotlinx.coroutines.*
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val downloadManager: DownloadManager, private val destinationFile : File,private val wallpaperManager: WallpaperManager) : BaseViewModel() {
    @RequiresApi(Build.VERSION_CODES.N)
    fun downloadWallpaper(downloadUrl : String){
        downloadImage(downloadUrl,downloadManager,destinationFile)
    }

    fun setWallpaper(wallpaperDrawable: Drawable) {
        viewModelScope.launch {
            setWallpaperBackground(wallpaperDrawable)
        }
    }

    private suspend fun setWallpaperBackground(wallpaperDrawable: Drawable) {

       withContext(Dispatchers.IO) {
           val bitmap = wallpaperDrawable.toBitmap()
           try {
               wallpaperManager.setBitmap(bitmap)
           } catch (e: Exception) {
               e.printStackTrace()
           }
        }
    }
}