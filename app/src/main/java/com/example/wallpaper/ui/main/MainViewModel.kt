package com.example.wallpaper.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.wallpaper.data.MainRepository
import com.example.wallpaper.network.model.ImageModel
import com.example.wallpaper.ui.base.BaseViewModel

import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {

    val mainLiveData: LiveData<PagedList<ImageModel>> =
        Transformations.map(mainRepository.imageLiveData) {
            it
        }

    fun getData() {
        mainRepository.fetchData()
    }
}