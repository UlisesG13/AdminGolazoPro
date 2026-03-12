package com.ulisesg.admingolazopro.features.auth.data.repositories

import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.api.AuthApi
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.mapper.toDomain
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models.LoginRequest
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models.RegisterRequest
import com.ulisesg.admingolazopro.features.auth.domain.entities.User
import com.ulisesg.admingolazopro.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthApi
) : AuthRepository {

    override suspend fun login(credentials: User): User {
        val request = LoginRequest(
            email = credentials.email,
            password = credentials.password
        )
        return remote.login(request).toDomain()
    }

    override suspend fun register(user: User): User {
        val request = RegisterRequest(
            name = user.nombre,
            email = user.email,
            password = user.password
        )
        return remote.register(request).toDomain()
    }
}