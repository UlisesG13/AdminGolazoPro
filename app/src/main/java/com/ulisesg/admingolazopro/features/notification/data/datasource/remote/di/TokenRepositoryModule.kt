package com.ulisesg.admingolazopro.features.notification.data.datasource.remote.di

import com.ulisesg.admingolazopro.features.notification.data.datasource.remote.api.FCMTokenDataSource
import com.ulisesg.admingolazopro.features.notification.data.repositories.FCMRepositoryImpl
import com.ulisesg.admingolazopro.features.notification.domain.repositories.FCMRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: FCMRepositoryImpl
    ): FCMRepository

    companion object {
        @Provides
        @Singleton
        fun provideFCMTokenDataSource(): FCMTokenDataSource {
            return FCMTokenDataSource()
        }
    }
}