package com.ulisesg.admingolazopro.features.auth.domain.entities


data class User(
    val id: String = "",
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val rol: Rol = Rol.PENDIENTE,
    val token: String? = null
)
