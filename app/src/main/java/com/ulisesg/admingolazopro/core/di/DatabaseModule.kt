package com.ulisesg.admingolazopro.core.di

import android.content.Context
import androidx.room.Room
import com.ulisesg.admingolazopro.core.database.AppDatabase
import com.ulisesg.admingolazopro.features.products.data.datasource.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideProductDao(
        database: AppDatabase
    ): ProductDao {
        return database.productDao()
    }

}