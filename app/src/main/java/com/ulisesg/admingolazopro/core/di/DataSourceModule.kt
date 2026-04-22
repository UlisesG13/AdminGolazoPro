package com.ulisesg.admingolazopro.core.di

import android.content.Context
import com.ulisesg.admingolazopro.features.products.data.datasource.local.ProductsLocalDataSource
import com.ulisesg.admingolazopro.features.products.data.datasource.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideProductsLocalDataSource(
        dao: ProductDao,
        @ApplicationContext context: Context
    ): ProductsLocalDataSource {

        return ProductsLocalDataSource(dao, context)
    }

}
