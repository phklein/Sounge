package com.sounge.soungeapp.rest

import com.sounge.soungeapp.request.CreateComment
import com.sounge.soungeapp.request.CreatePost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PostClient {
    @POST("/posts")
    fun createPost(
        @Body body: CreatePost
    ): Call<Long>

    @POST("/posts/{postId}/comments")
    fun createComment(
        @Path("postId") postId: Long,
        @Body body: CreateComment
    ): Call<Long>
}