package com.ulisesg.admingolazopro.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ulisesg.admingolazopro.features.products.data.datasource.local.dao.ProductDao
import com.ulisesg.admingolazopro.features.products.data.datasource.local.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}
