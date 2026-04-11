package com.ulisesg.admingolazopro.features.products.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.core.hardware.domain.VibratorRepository
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailUiState(
    val product: Product? = null,
    val categoryName: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val vibrator: VibratorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: String) {
        // Vibración ligera al entrar/cargar el detalle
        vibrator.vibrate(40)

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val productResult = repository.getProductById(productId)
                val categories = repository.getCategorias()

                productResult?.onSuccess { product ->
                    val category = categories.find { it.id == product.categoriaId }
                    _uiState.update { 
                        it.copy(
                            product = product, 
                            categoryName = category?.nombre,
                            isLoading = false 
                        ) 
                    }
                }?.onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "Error desconocido", isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Error desconocido", isLoading = false) }
            }
        }
    }
}
