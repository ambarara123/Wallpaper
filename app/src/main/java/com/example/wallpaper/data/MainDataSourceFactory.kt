package com.example.wallpaper.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.wallpaper.network.NetworkService
import com.example.wallpaper.model.ImageModel


class MainDataSourceFactory(private val networkService: NetworkService): DataSource.Factory<Int,ImageModel>(){

    private val liveDataSource = MutableLiveData<MainDataSource>()

    override fun create(): DataSource<Int, ImageModel> {
        val dataSource = MainDataSource(networkService)
        liveDataSource.postValue(dataSource)
        return dataSource
    }

}