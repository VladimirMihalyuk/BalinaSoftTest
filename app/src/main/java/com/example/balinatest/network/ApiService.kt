package com.example.balinatest.network

import com.example.balinatest.network.data_models.ApiResponse
import com.example.balinatest.network.data_models.ResponsePhoto
import retrofit2.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("/api/v2/photo/type")
    suspend fun getPhotoTypes(@Query("page") page: Int = 0): Response<ApiResponse>

    @Multipart
    @POST("/api/v2/photo")
    fun upload(
        @Part("name") name : RequestBody,
        @Part file: MultipartBody.Part,
        @Part("typeId") typeId : RequestBody
    ): Call<ResponsePhoto>
}