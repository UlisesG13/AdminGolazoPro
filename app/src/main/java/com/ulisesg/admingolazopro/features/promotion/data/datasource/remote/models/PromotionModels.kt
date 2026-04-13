package com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.models

import com.google.gson.annotations.SerializedName

data class PromocionCreateDTO(
    @SerializedName("codigo") val codigo: String,
    @SerializedName("descuento") val descuento: Float,
    @SerializedName("tipo_descuento") val tipo_descuento: String,
    @SerializedName("contador_usos") val contador_usos: Int = 0,
    @SerializedName("usos_maximos") val usos_maximos: Int,
    @SerializedName("fecha_inicio") val fecha_inicio: String,
    @SerializedName("fecha_expiracion") val fecha_expiracion: String,
    @SerializedName("esta_activa") val esta_activa: Boolean = false
)

data class PromocionUpdateDTO(
    @SerializedName("codigo") val codigo: String? = null,
    @SerializedName("descuento") val descuento: Float? = null,
    @SerializedName("tipo_descuento") val tipo_descuento: String? = null,
    @SerializedName("usos_maximos") val usos_maximos: Int? = null,
    @SerializedName("fecha_inicio") val fecha_inicio: String? = null,
    @SerializedName("fecha_expiracion") val fecha_expiracion: String? = null
)

data class PromocionDTO(
    @SerializedName("promocion_id") val promocion_id: Int,
    @SerializedName("codigo") val codigo: String,
    @SerializedName("descuento") val descuento: Float,
    @SerializedName("tipo_descuento") val tipo_descuento: String,
    @SerializedName("contador_usos") val contador_usos: Int,
    @SerializedName("usos_maximos") val usos_maximos: Int,
    @SerializedName("fecha_inicio") val fecha_inicio: String,
    @SerializedName("fecha_expiracion") val fecha_expiracion: String,
    @SerializedName("esta_activa") val esta_activa: Boolean
)

