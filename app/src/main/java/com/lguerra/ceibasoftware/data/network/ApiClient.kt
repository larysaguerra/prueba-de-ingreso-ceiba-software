package com.lguerra.ceibasoftware.data.network

import retrofit2.http.GET
import retrofit2.http.Query

private const val GET_USERS = "users"
private const val GET_POSTS = "posts"

interface ApiClient {

    @GET(GET_USERS)
    suspend fun getUsers(): List<UserResponse>

    @GET(GET_POSTS)
    suspend fun getPostsByUserId(@Query("userId") userId: Int): List<PostResponse>

}