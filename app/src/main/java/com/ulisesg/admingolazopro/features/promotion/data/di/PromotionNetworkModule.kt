package com.ulisesg.admingolazopro.features.promotion.data.di

import com.ulisesg.admingolazopro.features.promotion.data.datasource.remote.api.PromotionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PromotionNetworkModule {

    @Provides
    @Singleton
    fun providePromotionApi(retrofit: Retrofit): PromotionApi {
        return retrofit.create(PromotionApi::class.java)
    }
}
