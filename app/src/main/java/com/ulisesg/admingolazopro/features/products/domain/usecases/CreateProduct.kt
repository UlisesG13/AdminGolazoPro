package com.ulisesg.admingolazopro.features.products.domain.usecases

import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.entities.ProductImage
import com.ulisesg.admingolazopro.features.products.domain.repositories.ImageRepository
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

class CreateProduct @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(product: Product): Result<Product> {
        // 1. Subir imágenes y obtener sus IDs
        val imagenesSubidas = product.imagenes.mapNotNull { image ->
            image.bytes?.let { bytes ->
                imageRepository.uploadImagen(
                    bytes, image.orden,
                    filename = "producto_${System.currentTimeMillis()}_${image.orden}.jpg"
                )
            }
        }

        // 2. Crear el producto
        val result = productsRepository.createProduct(product)

        // 3. Asociar imágenes si el producto se creó bien
        result.onSuccess { productoCreado ->
            imagenesSubidas.forEach { imagen ->
                imageRepository.asociarImagenAProducto(
                    ProductImage(
                        productoImagenId = null,
                        productoId = productoCreado.id,
                        imagenId = imagen.id,
                        esPrincipal = imagen.orden == 1
                    )
                )
            }
        }
        return result
    }
}
