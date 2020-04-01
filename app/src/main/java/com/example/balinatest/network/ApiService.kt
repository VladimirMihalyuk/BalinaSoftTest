package com.example.balinatest.network

import com.example.balinatest.network.data_models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/v2/photo/type")
    suspend fun getPhotoTypes(@Query("page") page: Int = 30): Response<ApiResponse>
}