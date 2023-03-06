package com.lguerra.ceibasoftware.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey val userId: Int,
    val name: String,
    val phone: String,
    val email: String
) {
    companion object {
        const val TABLE_NAME = "user_table"
    }
}