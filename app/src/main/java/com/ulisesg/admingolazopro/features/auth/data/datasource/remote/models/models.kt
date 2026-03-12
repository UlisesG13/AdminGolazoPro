package com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val id: String,
    val name: String,
    val email: String,
    val rol: String,
    val token: String
)