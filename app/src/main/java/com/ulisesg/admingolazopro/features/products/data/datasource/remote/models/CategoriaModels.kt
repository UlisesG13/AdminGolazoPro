package com.ulisesg.admingolazopro.features.products.data.datasource.remote.models

data class CategoriaResponse(
    val categoria_id: Int?,
    val name: String?,
    val seccion_id: Int?
)

data class CategoriaRequest(
    val nombre: String,
    val seccion_id: Int
)
