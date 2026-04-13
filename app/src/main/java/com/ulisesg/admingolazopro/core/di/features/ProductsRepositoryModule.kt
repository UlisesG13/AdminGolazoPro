package com.ulisesg.admingolazopro.core.di.features

import com.ulisesg.admingolazopro.features.products.data.repositories.ImageRepositoryImpl
import com.ulisesg.admingolazopro.features.products.data.repositories.ProductsRepositoryImpl
import com.ulisesg.admingolazopro.features.products.domain.repositories.ImageRepository
import com.ulisesg.admingolazopro.features.products.domain.repositories.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductsRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository

    @Binds
    @Singleton
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}


