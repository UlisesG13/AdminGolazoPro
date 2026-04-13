package com.ulisesg.admingolazopro.features.products.domain.entities

data class ProductImage(
    val productoImagenId: Int?,
    val productoId: String,
    val imagenId: Int,
    val esPrincipal: Boolean,
)
