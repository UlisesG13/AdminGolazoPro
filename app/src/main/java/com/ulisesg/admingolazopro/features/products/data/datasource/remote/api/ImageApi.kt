package com.ulisesg.admingolazopro.features.products.data.datasource.remote.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApi {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): ImageUploadResponse
}

data class ImageUploadResponse(
    val url: String
)
