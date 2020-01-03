package com.example.wallpaper.network

import com.example.wallpaper.network.model.ImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("photos/")
    suspend fun getRandomImages(
        @Query("page") page: Int, @Query("per_page") perPage: Int
    ): List<ImageModel>

}