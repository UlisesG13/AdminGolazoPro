package com.ulisesg.admingolazopro.features.auth.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.auth.data.datasource.remote.models.AuthResponse
import com.ulisesg.admingolazopro.features.auth.domain.entities.Rol
import com.ulisesg.admingolazopro.features.auth.domain.entities.User

fun AuthResponse.toDomain(): User {
    return User(
        id = usuario_id,
        nombre = "",
        email = email,
        password = "",
        rol = Rol.entries.firstOrNull { it.value == rol } ?: Rol.ADMINISTRADOR,
        token = token
    )
}