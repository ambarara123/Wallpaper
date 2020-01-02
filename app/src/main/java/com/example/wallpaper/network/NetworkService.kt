package com.example.wallpaper.network

import com.example.wallpaper.network.model.ImageModel
import com.example.wallpaper.utils.API_KEY
import com.example.wallpaper.utils.CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("photos/")
    suspend fun getRandomImages(
        @Query("page") page: Int, @Query("per_page") perPage: Int
    ): List<ImageModel>

}