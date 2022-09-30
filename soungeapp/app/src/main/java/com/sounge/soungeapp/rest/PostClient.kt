package com.sounge.soungeapp.rest

import com.sounge.soungeapp.request.CreateComment
import com.sounge.soungeapp.request.CreatePost
import com.sounge.soungeapp.response.CommentSimple
import com.sounge.soungeapp.response.Page
import retrofit2.Call
import retrofit2.http.*

interface PostClient {
    @POST("/posts")
    fun createPost(
        @Body body: CreatePost
    ): Call<Long>

    @GET("/posts/{postId}/comments")
    fun getComments(
        @Path("postId") postId: Long,
        @Query("viewerId") viewerId: Long,
        @Query("page") page: Int
    ): Call<Page<CommentSimple>>

    @POST("/posts/{postId}/comments")
    fun createComment(
        @Path("postId") postId: Long,
        @Body body: CreateComment
    ): Call<Long>
}