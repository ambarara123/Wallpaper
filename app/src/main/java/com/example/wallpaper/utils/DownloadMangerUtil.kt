package com.example.wallpaper.utils

import android.app.DownloadManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber
import java.io.File

@RequiresApi(Build.VERSION_CODES.N)
fun downloadImage(url : String, downloadManager: DownloadManager,file : File){


    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle("Downloading Image")
        .setDescription("Downloading")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        .setDestinationUri(Uri.fromFile(file))

    downloadManager.enqueue(request).let {
        Timber.d("$it")
    }
}