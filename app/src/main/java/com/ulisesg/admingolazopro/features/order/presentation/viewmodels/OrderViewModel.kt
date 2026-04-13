package com.ulisesg.admingolazopro.features.order.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.features.order.domain.usecases.*
import com.ulisesg.admingolazopro.features.order.presentation.screens.OrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrderUiState())
    val state: StateFlow<OrderUiState> = _state.asStateFlow()

    init {
        loadAllOrders()
    }

    fun loadAllOrders() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getAllOrdersUseCase()
                .onSuccess { orders ->
                    _state.update { it.copy(isLoading = false, orders = orders) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error desconocido") }
                }
        }
    }

    fun updateStatus(pedidoId: Int, nuevoEstado: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            updateOrderStatusUseCase(pedidoId, nuevoEstado)
                .onSuccess {
                    loadAllOrders()
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }
}

