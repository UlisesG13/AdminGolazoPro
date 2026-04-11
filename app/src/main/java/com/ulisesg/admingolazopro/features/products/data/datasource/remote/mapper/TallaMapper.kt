package com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.TallaResponse
import com.ulisesg.admingolazopro.features.products.domain.entities.Talla

object TallaMapper {
    fun toDomain(dto: TallaResponse): Talla {
        return Talla(
            id = dto.id,
            nombre = dto.nombre
        )
    }
}