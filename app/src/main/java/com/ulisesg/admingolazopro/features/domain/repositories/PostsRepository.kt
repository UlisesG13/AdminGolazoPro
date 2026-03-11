package com.alilopez.demo.features.jsonplaceholder.domain.repositories

import com.alilopez.demo.features.jsonplaceholder.domain.entities.Posts
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
        fun getPosts(): Flow<List<Posts>>
        suspend fun syncPosts()
}