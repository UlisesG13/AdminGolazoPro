package com.ulisesg.admingolazopro.features.notification.data.datasource.remote.api

import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {
    @POST("users/device-token")
    suspend fun registerToken(
        @Query("token") token: String
    )
}
