package com.alilopez.kt_demohilt.features.jsonplaceholder.domain.usescases

import com.alilopez.demo.features.jsonplaceholder.domain.repositories.PostsRepository
import javax.inject.Inject

class SyncPostsUseCase @Inject constructor(
    private val repository: PostsRepository
) {
    suspend operator fun invoke() {
        repository.syncPosts()
    }
}