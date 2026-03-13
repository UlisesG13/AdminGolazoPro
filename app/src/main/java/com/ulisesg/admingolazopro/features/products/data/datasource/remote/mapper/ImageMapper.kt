package com.ulisesg.admingolazopro.features.products.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.products.data.datasource.remote.models.ImagenResponse
import com.ulisesg.admingolazopro.features.products.domain.entities.Image

object ImageMapper {

    fun toDomain(dto: ImagenResponse): Image {
        return Image(
            id = dto.imagen_id,
            path = dto.path,
            orden = dto.orden,
            bytes = null
        )
    }
}