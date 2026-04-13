package com.ulisesg.admingolazopro.features.products.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(

    @PrimaryKey
    val id: String,

    val nombre: String,
    val precio: Int,
    val descripcion: String,

    val categoriaId: Int,

    val estaActivo: Boolean,
    val esDestacado: Boolean,

    val imagenDestacada: String?,

    val fechaCreacion: String
)
