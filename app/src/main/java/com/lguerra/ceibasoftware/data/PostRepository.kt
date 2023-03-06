package com.lguerra.ceibasoftware.data

import com.lguerra.ceibasoftware.data.database.dao.PostDao
import com.lguerra.ceibasoftware.data.database.entities.PostEntity
import com.lguerra.ceibasoftware.data.network.ApiService
import com.lguerra.ceibasoftware.data.network.PostResponse
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val api: ApiService,
    private val postDao: PostDao
) {

    suspend fun getPostsFromApi(userId: Int): List<PostResponse> = api.getPostByUser(userId)

    suspend fun getPostsFromDatabase(userId: Int): List<PostEntity> = postDao.getAllPosts(userId)

    suspend fun insertPosts(posts: List<PostEntity>) = postDao.insertPosts(*posts.toTypedArray())

}