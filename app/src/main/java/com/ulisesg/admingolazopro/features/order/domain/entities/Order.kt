package com.ulisesg.admingolazopro.features.order.domain.entities

data class Order(
    val id: Int,
    val usuarioId: String,
    val promocionId: Int?,
    val estado: String,
    val fechaPedido: String?,
    val fechaActualizacion: String?,
    val subtotal: Float,
    val descuento: Float?,
    val total: Float?,
    val notas: String?,
    val direccion: Map<String, Any>?
)

