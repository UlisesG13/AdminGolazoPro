package com.alilopez.kt_demohilt.features.jsonplaceholder.domain.usescases

import com.alilopez.demo.features.jsonplaceholder.domain.usescases.GetPostsUseCase

data class PostUseCases(
    val getPosts: GetPostsUseCase,
    val syncPosts: SyncPostsUseCase
)