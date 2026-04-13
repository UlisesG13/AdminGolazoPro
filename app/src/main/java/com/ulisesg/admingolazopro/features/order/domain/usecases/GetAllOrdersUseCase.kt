package com.ulisesg.admingolazopro.features.order.domain.usecases

import com.ulisesg.admingolazopro.features.order.domain.entities.Order
import com.ulisesg.admingolazopro.features.order.domain.repositories.OrderRepository
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(): Result<List<Order>> {
        return repository.getAllOrders()
    }
}

