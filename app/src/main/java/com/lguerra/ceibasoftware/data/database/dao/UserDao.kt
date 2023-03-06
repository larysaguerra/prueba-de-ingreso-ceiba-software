package com.lguerra.ceibasoftware.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lguerra.ceibasoftware.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE userId = :userId")
    suspend fun getUser(userId: Int): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: UserEntity)

}