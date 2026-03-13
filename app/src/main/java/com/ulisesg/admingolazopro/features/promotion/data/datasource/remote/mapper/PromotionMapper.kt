package com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.mapper

import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.models.PromocionCreateDTO
import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.models.PromocionDTO
import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.models.PromocionUpdateDTO
import com.ulisesg.admingolazopro.features.promotion.domain.entities.Promotion

fun PromocionDTO.toDomain(): Promotion {
    return Promotion(
        id = promocion_id,
        codigo = codigo,
        descuento = descuento,
        tipoDescuento = tipo_descuento,
        contadorUsos = contador_usos,
        usosMaximos = usos_maximos,
        fechaInicio = fecha_inicio,
        fechaExpiracion = fecha_expiracion,
        estaActiva = esta_activa
    )
}

fun Promotion.toCreateDto(): PromocionCreateDTO {
    return PromocionCreateDTO(
        codigo = codigo,
        descuento = descuento,
        tipo_descuento = tipoDescuento,
        contador_usos = contadorUsos,
        usos_maximos = usosMaximos,
        fecha_inicio = fechaInicio,
        fecha_expiracion = fechaExpiracion,
        esta_activa = estaActiva
    )
}

fun Promotion.toUpdateDto(): PromocionUpdateDTO {
    return PromocionUpdateDTO(
        codigo = codigo,
        descuento = descuento,
        tipo_descuento = tipoDescuento,
        usos_maximos = usosMaximos,
        fecha_inicio = fechaInicio,
        fecha_expiracion = fechaExpiracion
    )
}

fun List<PromocionDTO>.toDomain(): List<Promotion> = map { it.toDomain() }
