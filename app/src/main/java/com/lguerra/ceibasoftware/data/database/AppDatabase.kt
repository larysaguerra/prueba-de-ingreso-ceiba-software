package com.lguerra.ceibasoftware.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lguerra.ceibasoftware.data.database.dao.PostDao
import com.lguerra.ceibasoftware.data.database.dao.UserDao
import com.lguerra.ceibasoftware.data.database.entities.PostEntity
import com.lguerra.ceibasoftware.data.database.entities.UserEntity

@Database(
    version = AppDatabase.DATABASE_VERSION,
    entities = [
        UserEntity::class,
        PostEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
    }

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao

}