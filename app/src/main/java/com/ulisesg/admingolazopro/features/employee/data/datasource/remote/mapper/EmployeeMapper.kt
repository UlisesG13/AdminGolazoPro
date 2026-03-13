package com.ulisesg.admingolazopro.features.employee.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.employee.data.datasource.remote.models.EmployeeResponse
import com.ulisesg.admingolazopro.features.employee.domain.entities.Employee

fun EmployeeResponse.toDomain(): Employee {
    return Employee(
        usuario_id = usuarioId ?: "",
        nombre = nombre ?: "",
        email = email ?: "",
        telefono = null,
        rol = rol ?: "vendedor",
        fecha_creacion = "",
        is_authenticated = estaActivo ?: false
    )
}

fun List<EmployeeResponse>.toDomain(): List<Employee> {
    return this.map { it.toDomain() }
}
