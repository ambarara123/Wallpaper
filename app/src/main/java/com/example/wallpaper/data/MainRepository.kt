package com.example.wallpaper.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.wallpaper.network.model.ImageModel
import com.example.wallpaper.network.NetworkService
import javax.inject.Inject

class MainRepository @Inject constructor(networkService: NetworkService) {

    private val pageSize = 10

    private lateinit var dataSourceFactory: MainDataSourceFactory

    private val pagedListConfig: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(2 * pageSize)
            .setPageSize(pageSize)
            .setPrefetchDistance(2)
            .setEnablePlaceholders(false)
            .build()
    }

    private val shouldLoadData = MutableLiveData<Boolean>()

    val imageLiveData = Transformations.switchMap(shouldLoadData) {
        dataSourceFactory = MainDataSourceFactory(networkService)
        LivePagedListBuilder<Int, ImageModel>(dataSourceFactory, pagedListConfig).build()
    }

    fun fetchData() {
        shouldLoadData.value = true
    }
}
