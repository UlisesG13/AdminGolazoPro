package com.ulisesg.admingolazopro.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ulisesg.admingolazopro.core.database.dao.PostDao
import com.ulisesg.admingolazopro.core.database.entities.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
