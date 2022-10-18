package com.sounge.soungeapp.rest

import com.sounge.soungeapp.data.UserPage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.Objects

interface UserClient {
    @GET("/users/{id}")
    fun getUserPage(
        @Path("id") id: Long,
        @Query("viewerId") viewerId: Long
    ): Call<UserPage>

    @GET("/users/{id}/newMatches")
    fun checkNewMatches(
        @Path("id") id: Long
    ): Call<List<Objects>>

    @POST("/users/{id}/likeUser/{likedId}")
    fun likeUser(
        @Path("id") id: Long,
        @Path("likedId") likedId: Long
    ): Call<ResponseBody>

    @POST("/users/{id}/likePost/{postId}")
    fun likePost(
        @Path("id") id: Long,
        @Path("postId") postId: Long,
    ): Call<ResponseBody>

    @DELETE("/users/{id}/likePost/{postId}")
    fun unlikePost(
        @Path("id") id: Long,
        @Path("postId") postId: Long,
    ): Call<ResponseBody>
}