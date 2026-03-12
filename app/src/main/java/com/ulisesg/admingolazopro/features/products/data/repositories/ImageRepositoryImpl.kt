package com.ulisesg.admingolazopro.features.products.data.repositories

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.api.ImageApi
import com.ulisesg.admingolazopro.features.products.domain.repositories.ImageRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remote: ImageApi
) : ImageRepository {

    override suspend fun uploadImage(file: File): Result<String> = runCatching {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        
        val response = remote.uploadImage(body)
        response.url
    }
}
