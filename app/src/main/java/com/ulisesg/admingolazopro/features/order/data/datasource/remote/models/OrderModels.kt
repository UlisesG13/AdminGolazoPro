package com.ulisesg.admingolazopro.features.order.data.datasource.remote.models

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("pedido_id") val id: Int,
    @SerializedName("usuario_id") val usuarioId: String,
    @SerializedName("promocion_id") val promocionId: Int?,
    @SerializedName("estado") val estado: String,
    @SerializedName("fecha_pedido") val fechaPedido: String?,
    @SerializedName("fecha_actualizacion") val fechaActualizacion: String?,
    @SerializedName("subtotal") val subtotal: Float,
    @SerializedName("descuento") val descuento: Float?,
    @SerializedName("total") val total: Float?,
    @SerializedName("notas") val notas: String?,
    @SerializedName("direccion") val direccion: Map<String, Any>?
)
