package com.ulisesg.admingolazopro.features.products.data.datasource.local.mapper

import com.ulisesg.admingolazopro.features.products.data.datasource.local.entity.ProductEntity
import com.ulisesg.admingolazopro.features.products.domain.entities.Product

fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        nombre = nombre,
        precio = precio,
        descripcion = descripcion,
        categoriaId = categoriaId,
        estaActivo = estaActivo,
        esDestacado = esDestacado,
        imagenes = emptyList(),
        fechaCreacion = fechaCreacion
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        nombre = nombre,
        precio = precio,
        descripcion = descripcion,
        categoriaId = categoriaId,
        estaActivo = estaActivo,
        esDestacado = esDestacado,
        imagenDestacada = imagenes.firstOrNull() as String?,
        fechaCreacion = fechaCreacion
    )
}