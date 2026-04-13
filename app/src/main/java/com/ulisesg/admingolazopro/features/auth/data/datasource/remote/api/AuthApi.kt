package com.ulisesg.admingolazopro.features.auth.data.datasource.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models.AuthResponse
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models.LoginRequest
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models.RegisterRequest

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("users/admins")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthResponse
}
