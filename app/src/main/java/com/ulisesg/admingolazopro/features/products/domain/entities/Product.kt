package com.ulisesg.admingolazopro.features.products.domain.entities

data class Product(
    val id: String = "",
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val imagenes: List<Image> = emptyList(),
    val tallas: List<Talla> = emptyList(),
    val colores: List<Color> = emptyList(),
    val esDestacado: Boolean = false,
    val estaActivo: Boolean = true,
    val fechaCreacion: String = "",
    val categoriaId: Int,
)
