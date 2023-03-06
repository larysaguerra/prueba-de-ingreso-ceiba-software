package com.lguerra.ceibasoftware.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lguerra.ceibasoftware.data.database.entities.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM ${PostEntity.TABLE_NAME} WHERE userId = :userId")
    suspend fun getAllPosts(userId: Int): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(vararg posts: PostEntity)

}