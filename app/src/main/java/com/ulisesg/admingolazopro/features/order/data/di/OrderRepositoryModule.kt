package com.ulisesg.admingolazopro.features.order.data.di

import com.ulisesg.admingolazopro.features.order.data.repositories.OrderRepositoryImpl
import com.ulisesg.admingolazopro.features.order.domain.repositories.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OrderRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}
