package com.ulisesg.admingolazopro.features.products.domain.entities

data class Product(
    val id: String = "",
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val imagenes: List<ProductImage> = emptyList(),
    val esDestacado: Boolean = false,
    val estaActivo: Boolean = true,
    val fecha_creacion: String = "",
    val categoria_id: Int
)

data class ProductImage(
    val id: Int,
    val path: String,
    val orden: Int
)
