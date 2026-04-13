package com.ulisesg.admingolazopro.features.order.domain.usecases

import com.ulisesg.admingolazopro.features.order.domain.entities.Order
import com.ulisesg.admingolazopro.features.order.domain.repositories.OrderRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(pedidoId: Int, estado: String): Result<Order> {
        return repository.updateOrderStatus(pedidoId, estado)
    }
}

