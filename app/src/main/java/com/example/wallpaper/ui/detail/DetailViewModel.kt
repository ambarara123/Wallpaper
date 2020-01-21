package com.example.wallpaper.ui.detail

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.ui.base.BaseViewModel
import com.example.wallpaper.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.security.Timestamp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val wallpaperManager: WallpaperManager,
    private val downloadMangerUtil: DownloadMangerUtil
) : BaseViewModel() {

    private val _liveDownloadID = MutableLiveData<Long>()

    private val _isImageDownloaded = MutableLiveData<Boolean>()

    private lateinit var disposable : Disposable

    private lateinit var imageName: String
    lateinit var imageUrl: String

    val liveDownloadID: LiveData<Long>
        get() = _liveDownloadID

    val isImageDownloaded: LiveData<Boolean>
        get() = _isImageDownloaded

    fun init(bundle: Bundle) {
        val imageName = bundle.getString(KEY_IMAGE_NAME)
        val imageUrl = bundle.getString(KEY_IMAGE_URL_LARGE)

        requireNotNull(imageName)
        requireNotNull(imageUrl)

        this.imageName = imageName
        this.imageUrl = imageUrl
    }

    fun checkIfImageDownloaded(imageName: String,imageType: ImageType){
       _isImageDownloaded.value = checkIfFileExists(imageName, imageType)
    }

    fun downloadWallpaper(downloadUrl: String, imageName: String): Long {
        return downloadMangerUtil.downloadImage(downloadUrl, imageName)
    }

    fun setWallpaper(downloadUrl: String, imageName: String, imageType: ImageType) {
        viewModelScope.launch {
            setWallpaperInBackground(downloadUrl, imageName, imageType)
        }
    }

    private suspend fun setWallpaperInBackground(
        downloadUrl: String,
        imageName: String,
        imageType: ImageType
    ) {
        when {
            checkIfFileExists(imageName, imageType) -> setWallpaperWithPath(imageName, imageType)
            else -> {
                val id = downloadWallpaper(downloadUrl, imageName)
                _liveDownloadID.value = id
            }
        }
    }

    private suspend fun setWallpaperWithPath(imageName: String, imageType: ImageType) {
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

    @SuppressLint("CheckResult")
    fun checkButtonClick(observable: Observable<Any>){
            observable.debounce(3,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    disposable = it
                }
                .subscribe ({
                    downloadWallpaper(imageUrl, imageName)
                    Timber.d("RxClickListener called!!")
                },{
                    it.printStackTrace()
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}