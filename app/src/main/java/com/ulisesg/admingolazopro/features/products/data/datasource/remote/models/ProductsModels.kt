package com.ulisesg.admingolazopro.features.products.data.datasource.remote.models

data class ImageReponse(
    val id: Int,
    val path: String,
    val orden: Int
)

data class ProductResponse(
    val producto_id: String?,
    val nombre: String,
    val precio: Int,
    val descripcion: String?,
    val esta_activo: Boolean,
    val esta_destacado: Boolean,
    val categoria_id: Int,
    val fecha_creacion: String,
    val imagenes: List<ImageReponse>? = emptyList()
)
