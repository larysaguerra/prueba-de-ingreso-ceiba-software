package com.lguerra.ceibasoftware.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(private val api: ApiClient) {

    suspend fun getUsers(): List<UserResponse> {
        return withContext(Dispatchers.IO) {
            val response = api.getUsers()
            response
        }
    }

    suspend fun getPostByUser(userId: Int): List<PostResponse> {
        return withContext(Dispatchers.IO) {
            val response = api.getPostsByUserId(userId)
            response
        }
    }

}