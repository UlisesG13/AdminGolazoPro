package com.alilopez.kt_demohilt.features.jsonplaceholder.data.datasources.local.mapper

import com.alilopez.demo.features.jsonplaceholder.domain.entities.Posts
import com.alilopez.kt_demohilt.core.database.entities.PostEntity

// Mapper: De Local (DB) a Dominio
fun PostEntity.toDomain() = Posts(id, title, body)