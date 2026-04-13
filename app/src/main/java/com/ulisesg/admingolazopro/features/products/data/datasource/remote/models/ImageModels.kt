package com.ulisesg.admingolazopro.features.products.data.datasource.remote.models

data class ProductoImagenCreateRequest(
    val producto_id: String,
    val imagen_id: Int,
    val es_principal: Boolean? = null
)

data class ImagenResponse(
    val imagen_id: Int,
    val path: String,
    val orden: Int
)

data class ProductoImagenResponse(
    val producto_imagen_id: Int,
    val producto_id: String,
    val imagen_id: Int,
    val es_principal: Boolean
)

