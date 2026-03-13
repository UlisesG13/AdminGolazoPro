package com.ulisesg.admingolazopro.features.products.domain.repositories

import com.ulisesg.admingolazopro.features.products.domain.entities.Image
import com.ulisesg.admingolazopro.features.products.domain.entities.ProductImage

interface ImagenRepository {
    suspend fun uploadImagen(file: ByteArray, orden: Int): Image
    suspend fun deleteImagen(imagenId: Int)
    suspend fun eliminarImagenesPorProducto(productoId: String)
    suspend fun asociarImagenAProducto(relation: ProductImage): ProductImage
    suspend fun desasociarImagenDeProducto(productoId: String, imagenId: Int)
}