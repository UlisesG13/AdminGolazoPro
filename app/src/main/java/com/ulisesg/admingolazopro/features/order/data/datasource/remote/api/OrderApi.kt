package com.ulisesg.admingolazopro.features.order.data.datasource.remote.api

import com.ulisesg.admingolazopro.features.order.data.datasource.remote.models.OrderDto
import retrofit2.http.*

interface OrderApi {

    @GET("pedidos/")
    suspend fun getAllOrders(): List<OrderDto>

    @PUT("pedidos/{pedido_id}/estado/{estado}")
    suspend fun updateOrderStatus(
        @Path("pedido_id") pedidoId: Int,
        @Path("estado") estado: String
    ): OrderDto
}

