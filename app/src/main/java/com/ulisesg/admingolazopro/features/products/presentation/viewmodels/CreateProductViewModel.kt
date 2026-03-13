package com.ulisesg.admingolazopro.features.products.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.core.hardware.domain.VibratorRepository
import com.ulisesg.admingolazopro.features.products.domain.entities.Image
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
    val imagenes: List<String> = emptyList(),
    val estaActivo: Boolean = true,
    val estaDestacado: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val message: String? = null
)

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val vibrator: VibratorRepository
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

    fun addImage(uri: String) {

        vibrator.vibrate(40)

        _uiState.update {
            it.copy(
                imagenes = it.imagenes + uri
            )
        }
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

            val images = state.imagenes.mapIndexed { index, uri ->

                Image(
                    id = 0,
                    path = uri,
                    orden = index,
                    bytes = null
                )
            }

            val product = Product(
                id = "",
                nombre = state.nombre,
                precio = state.precio.toInt(),
                descripcion = state.descripcion,
                estaActivo = state.estaActivo,
                esDestacado = state.estaDestacado,
                categoriaId = state.categoriaId.toInt(),
                imagenes = images,
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

            result.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            }
        }
    }
}