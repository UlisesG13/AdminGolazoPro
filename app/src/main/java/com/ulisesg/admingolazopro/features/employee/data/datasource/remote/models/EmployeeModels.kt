package com.ulisesg.admingolazopro.features.employee.data.datasource.remote.models

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName("usuario_id") val usuarioId: String?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("rol") val rol: String?,
    @SerializedName("esta_activo") val estaActivo: Boolean?
)

data class EmployeesPageResponse(
    @SerializedName("items") val items: List<EmployeeResponse>?,
    @SerializedName("total") val total: Int?,
    @SerializedName("page") val page: Int?
)

data class RegisterEmployeeRequest(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("rol") val rol: String,
    @SerializedName("password") val password: String
)

data class UpdateEmployeeRequest(
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("rol") val rol: String?,
    @SerializedName("esta_activo") val estaActivo: Boolean?
)
