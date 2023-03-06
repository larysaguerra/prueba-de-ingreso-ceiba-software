package com.lguerra.ceibasoftware.data.database.entities

import androidx.room.Entity

@Entity(tableName = PostEntity.TABLE_NAME, primaryKeys = ["postId", "userId"])
data class PostEntity(
    val postId: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    companion object {
        const val TABLE_NAME = "post_table"
    }
}