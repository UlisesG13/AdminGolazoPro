package com.ulisesg.admingolazopro.features.products.data.di

import com.ulisesg.admingolazopro.features.products.data.repositories.ProductsRepositoryImpl
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
}
