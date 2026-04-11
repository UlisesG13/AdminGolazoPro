package com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ColorResponse
import com.ulisesg.admingolazopro.features.products.domain.entities.Color

object ColorMapper {
    fun toDomain(dto: ColorResponse): Color {
        return Color(
            id = dto.id,
            nombre = dto.nombre
        )
    }
}