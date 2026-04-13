package com.ulisesg.admingolazopro.features.products.domain.entities

data class Image(
    val id: Int,
    val path: String,
    val orden: Int,
    val bytes: ByteArray? = null
)
