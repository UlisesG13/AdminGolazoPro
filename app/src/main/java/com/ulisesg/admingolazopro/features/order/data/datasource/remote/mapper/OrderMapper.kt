package com.ulisesg.admingolazopro.features.order.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.order.data.datasource.remote.models.OrderDto
import com.ulisesg.admingolazopro.features.order.domain.entities.Order

fun OrderDto.toDomain(): Order {
    return Order(
        id = id,
        usuarioId = usuarioId,
        promocionId = promocionId,
        estado = estado,
        fechaPedido = fechaPedido,
        fechaActualizacion = fechaActualizacion,
        subtotal = subtotal,
        descuento = descuento,
        total = total,
        notas = notas,
        direccion = direccion
    )
}

