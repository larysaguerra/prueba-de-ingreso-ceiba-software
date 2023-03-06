package com.lguerra.ceibasoftware.mappers

import com.lguerra.ceibasoftware.data.database.entities.PostEntity
import com.lguerra.ceibasoftware.data.model.Post
import com.lguerra.ceibasoftware.data.network.PostResponse

fun PostResponse.toPostEntity(): PostEntity? {
    val postId = this.postId ?: return null
    val userId = this.userId ?: return null
    val title = this.title ?: return null
    val body = this.body ?: return null

    return PostEntity(
        postId = postId,
        userId = userId,
        title = title,
        body = body
    )
}

fun PostEntity.toPost(): Post = Post(
    title = title,
    body = body
)
