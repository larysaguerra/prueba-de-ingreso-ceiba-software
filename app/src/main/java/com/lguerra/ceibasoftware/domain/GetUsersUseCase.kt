package com.lguerra.ceibasoftware.domain

import com.lguerra.ceibasoftware.data.UserRepository
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.mappers.toUser
import com.lguerra.ceibasoftware.mappers.toUserEntity
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(): List<User> {
        val userEntityList = repository.getUsersFromDatabase()

        return if (userEntityList.isNotEmpty()) {
            userEntityList.map { it.toUser() }
        } else {
            repository.getUsersFromApi()
                .mapNotNull { it.toUserEntity() }
                .run {
                    repository.insertUsers(this)
                    this
                }.map { it.toUser() }
        }

    }
}