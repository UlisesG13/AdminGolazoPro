package com.alilopez.demo.features.jsonplaceholder.domain.usescases

import com.alilopez.demo.features.jsonplaceholder.domain.entities.Posts
import com.alilopez.demo.features.jsonplaceholder.domain.repositories.PostsRepository
import com.alilopez.kt_demohilt.core.di.RickAndMortyRetrofit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPostsUseCase @Inject constructor(
    private val repository: PostsRepository
) {
    // Usamos el operador 'invoke' para llamar al caso de uso como una función
    operator fun invoke(): Flow<List<Posts>> {
        return repository.getPosts()
    }
}