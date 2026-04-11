package com.ulisesg.admingolazopro.features.products.data.datasource.remote.models

data class ProductoCreateRequest(
    val nombre: String,
    val precio: Int,
    val descripcion: String?,
    val esta_activo: Boolean,
    val esta_destacado: Boolean,
    val categoria_id: Int
)

data class ProductoUpdateRequest(
    val nombre: String?,
    val precio: Int?,
    val descripcion: String?,
    val esta_activo: Boolean?,
    val esta_destacado: Boolean?,
    val categoria_id: Int?
)

data class ProductoResponse(
    val producto_id: String?,
    val nombre: String,
    val precio: Int,
    val descripcion: String?,
    val esta_activo: Boolean,
    val esta_destacado: Boolean,
    val categoria_id: Int,
    val fecha_creacion: String,
    val imagenes: List<ImagenResponse>
)

data class CategoriaResponse(
    val categoria_id: Int,
    val nombre: String,
    val seccion_id: Int
)
