package com.ulisesg.admingolazopro.features.products.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import com.ulisesg.admingolazopro.features.products.presentation.components.ProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState

    init {
        loadProducts()
    }

    fun loadProducts() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

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
                        error = e.message
                    )
                }
            }
        }
    }

    fun changeDestacado(product: Product) {

        viewModelScope.launch {

            repository.changeDestacado(
                id = product.id,
                destacado = !product.esDestacado
            )
        }
    }

    fun changeStatus(product: Product) {

        viewModelScope.launch {

            repository.changeStatus(
                id = product.id,
                status = !product.estaActivo
            )
        }
    }

    fun deleteProduct(productId: String) {

        viewModelScope.launch {

            repository.deleteProduct(productId)
            loadProducts()
        }
    }


}