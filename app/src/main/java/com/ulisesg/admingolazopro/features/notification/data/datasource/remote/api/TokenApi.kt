package com.ulisesg.admingolazopro.features.notification.data.datasource.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import com.ulisesg.admingolazopro.features.notification.data.datasource.remote.models.TokenRequest

interface TokenApi {
    @POST("users/device-token")
    suspend fun registerToken(@Body request: TokenRequest)
}