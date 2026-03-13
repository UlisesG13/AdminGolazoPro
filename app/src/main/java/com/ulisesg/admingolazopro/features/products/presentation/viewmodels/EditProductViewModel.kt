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

data class EditProductUiState(
    val id: String = "",
    val nombre: String = "",
    val precio: String = "",
    val descripcion: String = "",
    val categoriaId: String = "",
    val imagenes: List<String> = emptyList(),
    val estaActivo: Boolean = true,
    val estaDestacado: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val vibrator: VibratorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProductUiState())
    val uiState: StateFlow<EditProductUiState> = _uiState

    fun loadProduct(id: String) {

        viewModelScope.launch {

            val result = repository.getProductById(id)

            result?.onSuccess { product ->
                _uiState.update {
                    it.copy(
                        id = product.id,
                        nombre = product.nombre,
                        precio = product.precio.toString(),
                        descripcion = product.descripcion,
                        categoriaId = product.categoriaId.toString(),
                        estaActivo = product.estaActivo,
                        estaDestacado = product.esDestacado,
                        imagenes = product.imagenes.map { img -> img.path }
                    )
                }
            }
        }
    }

    fun updateNombre(v: String) {
        _uiState.update { it.copy(nombre = v) }
    }

    fun updatePrecio(v: String) {
        _uiState.update { it.copy(precio = v) }
    }

    fun updateDescripcion(v: String) {
        _uiState.update { it.copy(descripcion = v) }
    }

    fun updateCategoria(v: String) {
        _uiState.update { it.copy(categoriaId = v) }
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

    fun updateProduct() {

        val state = _uiState.value
        val precio = state.precio.toIntOrNull()
        val categoriaId = state.categoriaId.toIntOrNull()

        if (precio == null || categoriaId == null) {
            _uiState.update { it.copy(error = "Por favor, ingrese un precio y categoría válidos") }
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
                id = state.id,
                nombre = state.nombre,
                precio = precio,
                descripcion = state.descripcion,
                estaActivo = state.estaActivo,
                esDestacado = state.estaDestacado,
                categoriaId = categoriaId,
                imagenes = images,
                fechaCreacion = ""
            )

            val result = repository.updateProduct(product)

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        success = true
                    )
                }
            }

            result.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}