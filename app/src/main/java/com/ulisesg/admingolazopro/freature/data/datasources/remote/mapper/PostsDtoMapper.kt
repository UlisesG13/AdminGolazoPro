package com.alilopez.demo.features.jsonplaceholder.data.datasources.remote.mapper

import com.alilopez.demo.features.jsonplaceholder.data.datasources.remote.models.PostsDto
import com.alilopez.demo.features.jsonplaceholder.domain.entities.Posts
import com.alilopez.kt_demohilt.core.database.entities.PostEntity


fun PostsDto.toDomain(): Posts {
    return Posts(
        id = this.id,
        title = this.title,
        body = this.body
    )
}

// Mapper: De Remoto (API) a Local (DB)
fun PostsDto.toEntity() = PostEntity(id, title, body)