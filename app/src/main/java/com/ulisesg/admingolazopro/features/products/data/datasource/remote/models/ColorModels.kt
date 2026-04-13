package com.ulisesg.admingolazopro.features.products.data.datasource.remote.models

data class ColorResponse(
    val id: Int,
    val nombre: String
)

data class ColorRequest(
    val nombre: String
)
