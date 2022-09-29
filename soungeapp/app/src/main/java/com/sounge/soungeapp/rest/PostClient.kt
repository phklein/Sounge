package com.sounge.soungeapp.rest

import com.sounge.soungeapp.request.CreateComment
import com.sounge.soungeapp.request.CreatePost
import com.sounge.soungeapp.response.CommentSimple
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostClient {
    @POST("/posts")
    fun createPost(
        @Body body: CreatePost
    ): Call<Long>

    @GET("/posts/{postId}/comments")
    fun getComments(
        @Path("postId") postId: Long
    ): Call<MutableList<CommentSimple>>

    @POST("/posts/{postId}/comments")
    fun createComment(
        @Path("postId") postId: Long,
        @Body body: CreateComment
    ): Call<Long>
}