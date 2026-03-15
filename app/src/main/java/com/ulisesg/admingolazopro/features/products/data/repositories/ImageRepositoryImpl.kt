package com.ulisesg.admingolazopro.features.products.data.repositories

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.api.ImagesApi
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper.ImageMapper
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoImagenCreateRequest
import com.ulisesg.admingolazopro.features.products.domain.entities.Image
import com.ulisesg.admingolazopro.features.products.domain.entities.ProductImage
import com.ulisesg.admingolazopro.features.products.domain.repositories.ImageRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: ImagesApi
) : ImageRepository {

    override suspend fun uploadImagen(file: ByteArray, orden: Int, filename: String): Image {
        val requestFile = file.toRequestBody("image/*".toMediaType())
        val multipart = MultipartBody.Part.createFormData(
            "file",
            filename,
            requestFile
        )
        val ordenPart = orden
            .toString()
            .toRequestBody("text/plain".toMediaType())
        val response = api.uploadImage(
            file = multipart,
            orden = ordenPart
        )
        return ImageMapper.toDomain(response)
    }

    override suspend fun deleteImagen(imagenId: Int) {
        api.eliminarImagen(imagenId)
    }

    override suspend fun eliminarImagenesPorProducto(productoId: String) {
        api.eliminarImagenesProducto(productoId)
    }

    override suspend fun asociarImagenAProducto(relation: ProductImage): ProductImage {
        val request = ProductoImagenCreateRequest(
            producto_id = relation.productoId,
            imagen_id = relation.imagenId,
            es_principal = relation.esPrincipal
        )
        val response = api.asociarImagen(request)
        return ProductImage(
            productoImagenId = response.producto_imagen_id,
            productoId = response.producto_id,
            imagenId = response.imagen_id,
            esPrincipal = response.es_principal
        )
    }

    override suspend fun desasociarImagenDeProducto(productoId: String, imagenId: Int) {
        api.desasociarImagen(productoId, imagenId)
    }
}