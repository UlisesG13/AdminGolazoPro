package com.ulisesg.admingolazopro.features.auth.data.di

import com.ulisesg.admingolazopro.features.auth.data.repositories.AuthRepositoryImpl
import com.ulisesg.admingolazopro.features.auth.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
