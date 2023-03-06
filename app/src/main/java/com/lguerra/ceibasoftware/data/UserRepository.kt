package com.lguerra.ceibasoftware.data

import com.lguerra.ceibasoftware.data.database.dao.UserDao
import com.lguerra.ceibasoftware.data.database.entities.UserEntity
import com.lguerra.ceibasoftware.data.network.ApiService
import com.lguerra.ceibasoftware.data.network.UserResponse
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService,
    private val userDao: UserDao
) {

    suspend fun getUsersFromApi(): List<UserResponse> = api.getUsers()

    suspend fun getUsersFromDatabase(): List<UserEntity> = userDao.getAllUsers()

    suspend fun getUserByIdFromDatabase(userId: Int): UserEntity = userDao.getUser(userId = userId)

    suspend fun insertUsers(users: List<UserEntity>) = userDao.insertUsers(*users.toTypedArray())

}