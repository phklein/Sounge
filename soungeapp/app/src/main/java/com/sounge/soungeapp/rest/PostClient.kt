package com.sounge.soungeapp.rest

import com.sounge.soungeapp.enums.GenreName
import com.sounge.soungeapp.request.CreateComment
import com.sounge.soungeapp.request.CreatePost
import com.sounge.soungeapp.request.UpdatePost
import com.sounge.soungeapp.response.CommentSimple
import com.sounge.soungeapp.response.Page
import com.sounge.soungeapp.response.PostSimple
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDateTime

interface PostClient {
    @POST("/posts")
    fun createPost(
        @Body body: CreatePost
    ): Call<Long>

    @PUT("/posts/{postId}")
    fun updatePost(
        @Path("postId") postId: Long,
        @Body body: UpdatePost
    ): Call<ResponseBody>

    @DELETE("/posts/{postId}")
    fun deletePost(
        @Path("postId") postId: Long
    ): Call<ResponseBody>

    @POST("/posts/{postId}/comments")
    fun createComment(
        @Path("postId") postId: Long,
        @Body body: CreateComment
    ): Call<Long>

    @GET("/posts/{postId}/comments")
    fun getComments(
        @Path("postId") postId: Long,
        @Query("viewerId") viewerId: Long,
        @Query("page") page: Int
    ): Call<Page<CommentSimple>>

    @GET("/posts")
    fun getPosts(
        @Query("userId") userId: Long,
        @Query("genre") genre: GenreName?,
        @Query("startDateTime") startDateTime: LocalDateTime?,
        @Query("endDateTime") endDateTime: LocalDateTime?,
        @Query("textLike") textLike: String?,
        @Query("page") page: Int
    ): Call<Page<PostSimple>>

    @PUT("/posts/{postId}/comments/{id}")
    fun updateComment(
        @Path("postId") postId: Long,
        @Path("id") commentId: Long,
        @Body body: UpdatePost
    ): Call<ResponseBody>

    @DELETE("/posts/{postId}/comments/{id}")
    fun deleteComment(
        @Path("postId") postId: Long,
        @Path("id") commentId: Long
    ): Call<ResponseBody>
}