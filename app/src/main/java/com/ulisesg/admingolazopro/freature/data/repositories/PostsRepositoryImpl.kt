package com.alilopez.demo.features.jsonplaceholder.data.repositories

import com.alilopez.demo.features.jsonplaceholder.data.datasources.remote.mapper.toEntity
import com.alilopez.demo.features.jsonplaceholder.domain.entities.Posts
import com.alilopez.demo.features.jsonplaceholder.domain.repositories.PostsRepository
import com.alilopez.kt_demohilt.core.database.dao.PostDao
import com.alilopez.kt_demohilt.features.jsonplaceholder.data.datasources.local.mapper.toDomain
import com.alilopez.kt_demohilt.features.jsonplaceholder.data.datasources.remote.api.JsonPlaceHolderApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PostsRepositoryImpl @Inject constructor(
        private val api: JsonPlaceHolderApi,
        private val dao: PostDao
) : PostsRepository {

    override fun getPosts(): Flow<List<Posts>> {
        return dao.getAllPosts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncPosts() {
        try {
            val remotePosts = api.getPosts()
            dao.insertPosts(remotePosts.map { it.toEntity() })
        } catch (e: Exception) {
            // Aquí puedes manejar errores de red (logs, etc.)
        }
    }
}