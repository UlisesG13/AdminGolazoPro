package com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("rol") val rol: String
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class AuthResponse(
    @SerializedName("usuario_id") val usuario_id: String,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("email") val email: String,
    @SerializedName("rol") val rol: String,
    @SerializedName("token") val token: String?
)
