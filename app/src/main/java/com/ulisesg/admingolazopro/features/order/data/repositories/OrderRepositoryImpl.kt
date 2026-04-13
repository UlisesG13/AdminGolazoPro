package com.ulisesg.admingolazopro.features.order.data.repositories

import com.ulisesg.admingolazopro.features.order.data.datasource.remote.api.OrderApi
import com.ulisesg.admingolazopro.features.order.data.datasource.remote.mapper.toDomain
import com.ulisesg.admingolazopro.features.order.domain.entities.Order
import com.ulisesg.admingolazopro.features.order.domain.repositories.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi
) : OrderRepository {

    override suspend fun updateOrderStatus(pedidoId: Int, estado: String): Result<Order> {
        return try {
            val response = api.updateOrderStatus(pedidoId, estado)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllOrders(): Result<List<Order>> {
        return try {
            val response = api.getAllOrders()
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

