package com.ulisesg.admingolazopro.features.order.presentation.screens

import com.ulisesg.admingolazopro.features.order.domain.entities.Order

data class OrderUiState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val error: String? = null,
    val isSuccess: Boolean = false
)

