package com.example.wallpaper.ui.detail

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.ui.base.BaseViewModel
import com.example.wallpaper.utils.DownloadMangerUtil
import kotlinx.coroutines.*
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val wallpaperManager: WallpaperManager,
    private val downloadMangerUtil: DownloadMangerUtil
) : BaseViewModel() {

    private val _liveDownloadID = MutableLiveData<Long>()

    val liveDownloadID : LiveData<Long>
    get() = _liveDownloadID

    fun downloadWallpaper(downloadUrl: String, imageName: String) : Long {
       return downloadMangerUtil.downloadImage(downloadUrl, imageName)
    }

    fun setWallpaper(downloadUrl: String, imageName: String, imageType: String) {
        viewModelScope.launch {
            setWallpaperBackground(downloadUrl, imageName, imageType)
        }
    }

    private suspend fun setWallpaperBackground(
        downloadUrl: String,
        imageName: String,
        imageType: String
    ) {
        when {
            checkIfFileExists(imageName, imageType) -> setWallpaperWithPath(imageName, imageType)
            else -> {
               val id =  downloadWallpaper(downloadUrl, imageName)
                _liveDownloadID.value = id
            }
        }
    }

    private suspend fun setWallpaperWithPath(imageName: String, imageType: String) {
        withContext(Dispatchers.IO) {
            val bitmap =
                BitmapFactory.decodeFile(getPath(imageName, imageType))
            try {
                wallpaperManager.setBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun checkIfFileExists(imageName: String, imageType: String): Boolean {
        val pathName = getPath(imageName, imageType)

        val file = File(pathName)
        if (file.exists()) {
            return true
        }
        return false
    }

    private fun getPath(imageName: String, imageType: String): String {
        val pathName = "/sdcard/Android/data/com.example.wallpaper/files/$imageName.$imageType"
        return pathName
    }
}