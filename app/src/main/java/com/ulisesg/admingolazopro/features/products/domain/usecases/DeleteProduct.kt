package com.ulisesg.admingolazopro.features.products.domain.usecases

import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.repositories.ImagenRepository
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import javax.inject.Inject

// Opción simplificada
class DeleteProduct @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val imagenRepository: ImagenRepository
) {
    suspend operator fun invoke(product: Product): Result<Unit> {
        // desasociar imagenes
        for (image in product.imagenes) {
            imagenRepository.desasociarImagenDeProducto(product.id, image.id)
        }
        // borrar imagenes
        imagenRepository.eliminarImagenesPorProducto(product.id)
        // borrar producto
        return productsRepository.deleteProduct(product.id)
    }
}