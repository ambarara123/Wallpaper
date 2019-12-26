package com.example.wallpaper.network

import com.example.wallpaper.model.ImageModel
import com.example.wallpaper.utils.CLIENT_ID
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("photos/")
    suspend fun getRandomImages(@Query(CLIENT_ID)apiKey : String, @Query("page")page : Int, @Query("per_page")perPage : Int) : List<ImageModel>
}