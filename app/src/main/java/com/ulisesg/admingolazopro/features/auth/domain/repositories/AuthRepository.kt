package com.ulisesg.admingolazopro.features.auth.domain.repositories

import com.ulisesg.admingolazopro.features.auth.domain.entities.User

interface AuthRepository {
    suspend fun login(credentials: User): User
    suspend fun register(user: User): User
}
