package com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.di

import com.ulisesg.admingolazopro.features.promotion.data.repositories.PromotionRepositoryImpl
import com.ulisesg.admingolazopro.features.promotion.domain.repositories.PromotionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PromotionRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPromotionRepository(
        promotionRepositoryImpl: PromotionRepositoryImpl
    ): PromotionRepository
}