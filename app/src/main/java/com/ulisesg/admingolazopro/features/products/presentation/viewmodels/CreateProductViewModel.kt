package com.ulisesg.admingolazopro.features.products.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulisesg.admingolazopro.core.hardware.domain.VibratorRepository
import com.ulisesg.admingolazopro.features.products.domain.entities.Image
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.entities.ProductImage
import com.ulisesg.admingolazopro.features.products.domain.repositories.ImageRepository
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val success: Boolean = false
)

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val productRepository: ProductsRepository,
    private val imagenRepository: ImageRepository,
    private val vibrator: VibratorRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateProductUiState())
    val uiState: StateFlow<CreateProductUiState> = _uiState.asStateFlow()

    fun updateNombre(value: String) = _uiState.update { it.copy(nombre = value) }
    fun updatePrecio(value: String) = _uiState.update { it.copy(precio = value) }
    fun updateDescripcion(value: String) = _uiState.update { it.copy(descripcion = value) }
    fun updateCategoria(value: String) = _uiState.update { it.copy(categoriaId = value) }
    fun toggleActivo() = _uiState.update { it.copy(estaActivo = !it.estaActivo) }
    fun toggleDestacado() = _uiState.update { it.copy(estaDestacado = !it.estaDestacado) }

    fun addImage(uri: Uri) {
        vibrator.vibrate(40)
        _uiState.update { it.copy(imagenes = it.imagenes + uri.toString()) }
    }

    fun removeImage(uri: String) {
        _uiState.update { it.copy(imagenes = it.imagenes - uri) }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }

    fun createProduct() {
        val state = _uiState.value

        if (state.nombre.isBlank() || state.precio.isBlank() || state.categoriaId.isBlank()) {
            _uiState.update { it.copy(error = "Nombre, precio y categoría son obligatorios") }
            return
        }
        val precioInt = state.precio.toIntOrNull()
        val categoriaIdInt = state.categoriaId.toIntOrNull()
        if (precioInt == null || categoriaIdInt == null) {
            _uiState.update { it.copy(error = "Precio o Categoría ID inválidos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // ── Paso 1: Subir imágenes ────────────────────────────────────────────
            val uploadedImages = mutableListOf<Image>()
            try {
                state.imagenes.forEachIndexed { index, uriString ->
                    val uri = Uri.parse(uriString)
                    val bytes = context.contentResolver
                        .openInputStream(uri)
                        ?.use { it.readBytes() }
                        ?: throw Exception("No se pudo leer la imagen ${index + 1}")

                    // Nombre único: timestamp + índice para evitar colisiones en el servidor
                    val filename = "producto_${System.currentTimeMillis()}_${index}.jpg"

                    val imagen = imagenRepository.uploadImagen(
                        file = bytes,
                        orden = index,
                        filename = filename
                    )
                    uploadedImages.add(imagen)
                }
            } catch (e: Exception) {
                // Rollback: eliminar las imágenes que sí se subieron
                uploadedImages.forEach { runCatching { imagenRepository.deleteImagen(it.id) } }
                _uiState.update {
                    it.copy(isLoading = false, error = "Error subiendo imágenes: ${e.message}")
                }
                return@launch
            }

            // ── Paso 2: Crear producto ────────────────────────────────────────────
            val product = Product(
                id = "",
                nombre = state.nombre,
                precio = precioInt,
                descripcion = state.descripcion,
                estaActivo = state.estaActivo,
                esDestacado = state.estaDestacado,
                categoriaId = categoriaIdInt,
                imagenes = emptyList(),
                fechaCreacion = ""
            )
            val result = productRepository.createProduct(product)

            result.onSuccess { createdProduct ->
                // ── Paso 3: Asociar imágenes al producto ──────────────────────────
                try {
                    uploadedImages.forEachIndexed { index, imagen ->
                        imagenRepository.asociarImagenAProducto(
                            ProductImage(
                                productoImagenId = null,
                                productoId = createdProduct.id,
                                imagenId = imagen.id,
                                esPrincipal = index == 0  // La primera imagen es la principal
                            )
                        )
                    }
                    _uiState.update { it.copy(isLoading = false, success = true) }
                } catch (e: Exception) {
                    // El producto ya existe: no se puede hacer rollback limpio.
                    // Se informa al usuario; las imágenes están huérfanas pero el producto existe.
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Producto creado, pero falló la asociación de imágenes: ${e.message}"
                        )
                    }
                }
            }

            result.onFailure { error ->
                // Rollback: el producto no se creó, limpiar imágenes subidas
                uploadedImages.forEach { runCatching { imagenRepository.deleteImagen(it.id) } }
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}