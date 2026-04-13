package com.ulisesg.admingolazopro.features.auth.data.repositories

import com.ulisesg.admingolazopro.core.auth.TokenManager
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.api.AuthApi
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.mapper.toDomain
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models.LoginRequest
import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models.RegisterRequest
import com.ulisesg.admingolazopro.features.auth.domain.entities.User
import com.ulisesg.admingolazopro.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(credentials: User): User {
        val request = LoginRequest(
            email = credentials.email,
            password = credentials.password
        )
        val response = remote.login(request)
        response.token?.let { tokenManager.saveToken(it) }
        return response.toDomain()
    }

    override suspend fun register(user: User): User {
        val request = RegisterRequest(
            nombre = user.nombre,
            email = user.email,
            password = user.password,
            rol = user.rol.value // Se cambió .name por .value para enviar "administrador" en lugar de "ADMINISTRADOR"
        )
        val response = remote.register(request)
        response.token?.let { tokenManager.saveToken(it) }
        return response.toDomain()
    }
}
