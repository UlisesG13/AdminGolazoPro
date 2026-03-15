package com.ulisesg.admingolazopro.features.order.domain.repositories

import com.ulisesg.admingolazopro.features.order.domain.entities.Order

interface OrderRepository {
    suspend fun updateOrderStatus(pedidoId: Int, estado: String): Result<Order>
    suspend fun getAllOrders(): Result<List<Order>>
}
