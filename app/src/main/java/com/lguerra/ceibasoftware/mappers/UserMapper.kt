package com.lguerra.ceibasoftware.mappers

import com.lguerra.ceibasoftware.data.database.entities.UserEntity
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.data.network.UserResponse

fun UserResponse.toUserEntity(): UserEntity? {
    val userId = this.id ?: return null
    val name = this.name ?: return null
    val phone = this.phone ?: return null
    val email = this.email ?: return null

    return UserEntity(
        userId = userId,
        name = name,
        phone = phone,
        email = email
    )
}

fun UserEntity.toUser(): User = User(
    id = userId,
    name = name,
    phone = phone,
    email = email
)
