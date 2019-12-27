package com.example.wallpaper.utils

import java.io.File

fun checkIfFileExists(imageName: String, imageType: ImageType): Boolean {
    val pathName = getPath(imageName, imageType)

    val file = File(pathName)
    if (file.exists()) {
        return true
    }
    return false
}

fun getPath(imageName: String, imageType: ImageType): String {
    return "$FILES_PATH$imageName.${imageType.value}"
}