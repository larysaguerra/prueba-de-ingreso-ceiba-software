package com.lguerra.ceibasoftware.data.network

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("userId") val userId: Int?,
    @SerializedName("id") val postId: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("body") val body: String?
)