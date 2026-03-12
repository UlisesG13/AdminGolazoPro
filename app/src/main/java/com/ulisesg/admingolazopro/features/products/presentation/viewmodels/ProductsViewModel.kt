package com.ulisesg.admingolazopro.features.products.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.usecases.CreateProduct
import com.ulisesg.admingolazopro.features.products.domain.usecases.DeleteProduct
import com.ulisesg.admingolazopro.features.products.domain.usecases.GetProducts
import com.ulisesg.admingolazopro.features.products.domain.usecases.UpdateProduct
import com.ulisesg.admingolazopro.features.products.presentation.screens.ProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProducts,
    private val createProductUseCase: CreateProduct,
    private val updateProductUseCase: UpdateProduct,
    private val deleteProductUseCase: DeleteProduct
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsUiState())
    val state: StateFlow<ProductsUiState> = _state.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val products = getProductsUseCase()
                _state.update { it.copy(isLoading = false, products = products) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Error al cargar productos") }
            }
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            createProductUseCase(product)
                .onSuccess { newProduct ->
                    _state.update { 
                        it.copy(
                            isLoading = false, 
                            products = it.products + newProduct,
                            isSuccess = true 
                        ) 
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error al crear producto") }
                }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            updateProductUseCase(product)
                .onSuccess { updatedProduct ->
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            products = state.products.map { if (it.id == updatedProduct.id) updatedProduct else it },
                            isSuccess = true
                        )
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error al actualizar producto") }
                }
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            deleteProductUseCase(id)
                .onSuccess {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            products = state.products.filter { it.id != id }
                        )
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error al eliminar producto") }
                }
        }
    }
    
    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}
