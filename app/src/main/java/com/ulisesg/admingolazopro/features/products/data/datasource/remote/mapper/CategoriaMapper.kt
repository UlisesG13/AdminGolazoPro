package com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.CategoriaResponse
import com.ulisesg.admingolazopro.features.products.domain.entities.Category

object CategoriaMapper {
    fun toDomain(dto: CategoriaResponse): Category {
        return Category(
            id = dto.categoria_id ?: 0,
            nombre = dto.name ?: "",
            categoria_id = dto.seccion_id ?: 0
        )
    }
}
