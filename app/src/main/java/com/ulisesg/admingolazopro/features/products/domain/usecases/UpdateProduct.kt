package com.ulisesg.admingolazopro.features.products.domain.usecases

import com.ulisesg.admingolazopro.features.products.domain.entities.Image
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.entities.ProductImage
import com.ulisesg.admingolazopro.features.products.domain.repositories.ImagenRepository
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

class UpdateProduct @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val imagenRepository: ImagenRepository
) {
    suspend operator fun invoke(
        product: Product,
        imagenesNuevas: List<Image>,
        imagenesEliminadas: List<Image>
    ): Result<Product> {
        // 1. Eliminar imágenes removidas
        imagenesEliminadas.forEach { imagen ->
            imagen.id.let { imagenRepository.desasociarImagenDeProducto(product.id, imagen.id) }
            imagen.id.let { imagenRepository.deleteImagen(it) }
        }

        // 2. Subir y asociar imágenes nuevas
        imagenesNuevas.forEach { image ->
            image.bytes?.let { bytes ->
                val subida = imagenRepository.uploadImagen(bytes, image.orden)
                imagenRepository.asociarImagenAProducto(
                    ProductImage(
                        productoImagenId = null,
                        productoId = product.id,
                        imagenId = subida.id,
                        esPrincipal = subida.orden == 1
                    )
                )
            }
        }

        // 3. Actualizar datos del producto
        return productsRepository.updateProduct(product)
    }
}