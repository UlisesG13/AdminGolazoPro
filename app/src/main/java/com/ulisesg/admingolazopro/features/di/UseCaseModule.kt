package com.alilopez.kt_demohilt.features.jsonplaceholder.di

import com.alilopez.demo.features.jsonplaceholder.domain.repositories.PostsRepository
import com.alilopez.demo.features.jsonplaceholder.domain.usescases.GetPostsUseCase
import com.alilopez.kt_demohilt.features.jsonplaceholder.domain.usescases.PostUseCases
import com.alilopez.kt_demohilt.features.jsonplaceholder.domain.usescases.SyncPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providePostUseCases(repository: PostsRepository): PostUseCases {
        return PostUseCases(
            getPosts = GetPostsUseCase(repository),
            syncPosts = SyncPostsUseCase(repository)
        )
    }
}