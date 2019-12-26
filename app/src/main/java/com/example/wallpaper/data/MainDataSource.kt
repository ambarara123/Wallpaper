package com.example.wallpaper.data

import androidx.paging.ItemKeyedDataSource
import com.example.wallpaper.network.NetworkService
import com.example.wallpaper.model.ImageModel
import com.example.wallpaper.utils.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainDataSource(private val networkService: NetworkService) : ItemKeyedDataSource<Int, ImageModel>() {

    private var page = 1
    private val networkScope = CoroutineScope(Dispatchers.IO)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<ImageModel>
    ) {
        val key = params.requestedInitialKey ?: 1
        networkScope.launch {
            val result = networkService.getRandomImages(API_KEY, key, 10)
            callback.onResult(result)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ImageModel>) {
        networkScope.launch {
            val result = networkService.getRandomImages(API_KEY, params.key, 10)
            callback.onResult(result)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ImageModel>) {

    }

    override fun getKey(item: ImageModel): Int {
        return page++
    }

}