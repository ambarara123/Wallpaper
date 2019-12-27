package com.example.wallpaper.utils

const val BASE_URL = "https://api.unsplash.com/"
const val API_KEY = "65cb8fd0add614ed645d43f7cf5129af9c682abd846c7b7239e2752126df6181"
const val CLIENT_ID = "client_id"
const val KEY_IMAGE_URL_SMALL = "KEY_IMAGE_URL_SMALL"
const val KEY_IMAGE_URL_LARGE = "KEY_IMAGE_URL_LARGE"
const val KEY_IMAGE_DESCRIPTION = "KEY_IMAGE_DESCRIPTION"
const val KEY_IMAGE_BIO = "KEY_IMAGE_BIO"
const val KEY_IMAGE_NAME = "KEY_IMAGE_NAME"
const val KEY_INTENT_BUNDLE = "KEY_INTENT_BUNDLE"

const val FILES_PATH = "/sdcard/Android/data/com.example.wallpaper/files/"

sealed class ImageType {
    abstract val value: String

    object JPG : ImageType() {
        override val value: String = "jpg"
    }

    object PNG : ImageType() {
        override val value: String = "png"
    }

    object JPEG : ImageType() {
        override val value: String = "jpeg"
    }

}
