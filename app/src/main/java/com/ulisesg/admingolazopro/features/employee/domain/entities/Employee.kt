package com.ulisesg.admingolazopro.features.employee.domain.entities

data class Employee(
    val usuario_id: String,
    val nombre: String,
    val email: String,
    val telefono: String?,
    val rol: String,
    val fecha_creacion: String,
    val is_authenticated: Boolean? = false,
    val google_id: String? = null,
    val fecha_eliminacion: String? = null
)

