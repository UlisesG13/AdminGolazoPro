package com.ulisesg.admingolazopro.features.promotion.domain.entities

data class Promotion(
    val id: Int = 0,
    val codigo: String,
    val descuento: Float,
    val tipoDescuento: String,
    val contadorUsos: Int = 0,
    val usosMaximos: Int,
    val fechaInicio: String,
    val fechaExpiracion: String,
    val estaActiva: Boolean = false
)

