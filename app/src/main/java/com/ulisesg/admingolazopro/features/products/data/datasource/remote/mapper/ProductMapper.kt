package com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoCreateRequest
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoResponse
import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ProductoUpdateRequest
import com.ulisesg.admingolazopro.features.products.domain.entities.Product

object ProductMapper {

    fun toDomain(dto: ProductoResponse): Product {

        val images = dto.imagenes.map {
            ImageMapper.toDomain(it)
        }

        val tallas = dto.tallas.map {
            TallaMapper.toDomain(it)
        }

        val colores = dto.colores.map {
            ColorMapper.toDomain(it)
        }
        
        return Product(
            id = dto.producto_id,
            nombre = dto.nombre,
            precio = dto.precio,
            descripcion = dto.descripcion ?: "",
            estaActivo = dto.esta_activo,
            esDestacado = dto.esta_destacado,
            categoriaId = dto.categoria_id,
            fechaCreacion = dto.fecha_creacion,
            imagenes = images,
            tallas = tallas,
            colores = colores
        )
    }

    fun toCreateRequest(product: Product): ProductoCreateRequest {
        return ProductoCreateRequest(
            nombre = product.nombre,
            precio = product.precio,
            descripcion = product.descripcion,
            esta_activo = product.estaActivo,
            esta_destacado = product.esDestacado,
            categoria_id = product.categoriaId
        )
    }

    fun toUpdateRequest(product: Product): ProductoUpdateRequest {
        return ProductoUpdateRequest(
            nombre = product.nombre,
            precio = product.precio,
            descripcion = product.descripcion,
            esta_activo = product.estaActivo,
            esta_destacado = product.esDestacado,
            categoria_id = product.categoriaId
        )
    }
}
