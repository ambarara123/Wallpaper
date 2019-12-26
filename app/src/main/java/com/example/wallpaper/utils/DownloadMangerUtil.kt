package com.example.wallpaper.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class DownloadMangerUtil(private val context: Context) {

    private val downloadManager: DownloadManager by lazy {
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    fun downloadImage(
        url: String,
        fileName: String? = null,
        fileType: String = "png"
    ) : Long {
        val file = getFile(fileName, fileType)
        val request = createDownloadRequest(url, file)
        return downloadManager.enqueue(request)
    }

    private fun createDownloadRequest(
        url: String,
        file: File
    ): DownloadManager.Request? {
        return DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading Image")
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(Uri.fromFile(file))
    }

    private fun getFile(fileName: String?, fileType: String): File {
        val name = fileName ?: "default"
        return File(context.getExternalFilesDir(null), "${name}.$fileType")
    }
}