package com.ulisesg.admingolazopro.features.products.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateProductUiState(
    val nombre: String = "",
    val precio: String = "",
    val descripcion: String = "",
    val categoriaId: String = "",
    val estaActivo: Boolean = true,
    val estaDestacado: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val message: String? = null
) {
}

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateProductUiState())
    val uiState: StateFlow<CreateProductUiState> = _uiState

    fun updateNombre(value: String) {
        _uiState.update { it.copy(nombre = value) }
    }

    fun updatePrecio(value: String) {
        _uiState.update { it.copy(precio = value) }
    }

    fun updateDescripcion(value: String) {
        _uiState.update { it.copy(descripcion = value) }
    }

    fun updateCategoria(value: String) {
        _uiState.update { it.copy(categoriaId = value) }
    }

    fun toggleActivo() {
        _uiState.update { it.copy(estaActivo = !it.estaActivo) }
    }

    fun toggleDestacado() {
        _uiState.update { it.copy(estaDestacado = !it.estaDestacado) }
    }

    fun createProduct() {

        val state = _uiState.value

        if (
            state.nombre.isBlank() ||
            state.precio.isBlank() ||
            state.categoriaId.isBlank()
        ) {
            _uiState.update { it.copy(error = "Campos requeridos incompletos") }
            return
        }

        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null) }

            val product = Product(
                id = "",
                nombre = state.nombre,
                precio = state.precio.toInt(),
                descripcion = state.descripcion,
                estaActivo = state.estaActivo,
                esDestacado = state.estaDestacado,
                categoriaId = state.categoriaId.toInt(),
                imagenes = emptyList(),
                fechaCreacion = ""
            )

            val result = repository.createProduct(product)

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        success = true
                    )
                }
            }

            result.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = it.message
                    )
                }
            }
        }
    }
}

