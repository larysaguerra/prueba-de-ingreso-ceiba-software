package com.lguerra.ceibasoftware.domain

import com.lguerra.ceibasoftware.data.PostRepository
import com.lguerra.ceibasoftware.data.UserRepository
import com.lguerra.ceibasoftware.data.model.Post
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.mappers.toPost
import com.lguerra.ceibasoftware.mappers.toPostEntity
import com.lguerra.ceibasoftware.mappers.toUser
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(userId: Int): Pair<User, List<Post>> {
        val user = userRepository.getUserByIdFromDatabase(userId).toUser()
        val postEntityList = postRepository.getPostsFromDatabase(userId)

        val posts = if (postEntityList.isNotEmpty()) {
            postEntityList.map { it.toPost() }
        } else {
            postRepository.getPostsFromApi(userId)
                .mapNotNull { it.toPostEntity() }
                .run {
                    postRepository.insertPosts(this)
                    this
                }.map { it.toPost() }
        }

        return Pair(user, posts)
    }

}