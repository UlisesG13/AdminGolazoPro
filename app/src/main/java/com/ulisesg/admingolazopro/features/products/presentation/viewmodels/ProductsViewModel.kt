package com.ulisesg.admingolazopro.features.products.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.core.hardware.domain.VibratorRepository
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val vibrator: VibratorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val products = repository.getProducts()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        products = products
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun deleteProduct(productId: String) {
        // Vibramos inmediatamente en el hilo principal para mayor respuesta táctil
        Log.d("ProductsViewModel", "Ejecutando vibración para eliminación")
        vibrator.vibrate(60) 
        
        viewModelScope.launch {
            try {
                repository.deleteProduct(productId)
                loadProducts()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
