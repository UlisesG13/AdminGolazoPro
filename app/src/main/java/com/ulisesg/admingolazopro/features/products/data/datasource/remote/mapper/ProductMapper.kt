package com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ImageReponse
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductResponse
import com.ulisesg.admingolazopro.features.products.domain.entities.Product
import com.ulisesg.admingolazopro.features.products.domain.entities.ProductImage

fun ProductResponse.toDomain(): Product {
    return Product(
        id = producto_id ?: "",
        nombre = nombre,
        descripcion = descripcion ?: "",
        precio = precio,
        imagenes = imagenes?.map { it.toDomain() } ?: emptyList(),
        esDestacado = esta_destacado,
        estaActivo = esta_activo,
        fecha_creacion = fecha_creacion,
        categoria_id = categoria_id
    )
}

fun ImageReponse.toDomain(): ProductImage {
    return ProductImage(
        id = id,
        path = path,
        orden = orden
    )
}

fun Product.toResponse(): ProductResponse {
    return ProductResponse(
        producto_id = if (id.isEmpty()) null else id,
        nombre = nombre,
        precio = precio,
        descripcion = descripcion,
        esta_activo = estaActivo,
        esta_destacado = esDestacado,
        categoria_id = categoria_id,
        fecha_creacion = fecha_creacion,
        imagenes = imagenes.map { ImageReponse(it.id, it.path, it.orden) }
    )
}

fun List<ProductResponse>.toDomain(): List<Product> {
    return this.map { it.toDomain() }
}
