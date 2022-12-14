package com.sounge.soungeapp.rest

import com.sounge.soungeapp.data.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserClient {
    @GET("/users/{id}")
    fun getUserPage(
        @Path("id") id: Long,
        @Query("viewerId") viewerId: Long
    ): Call<UserPage>

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

    @POST("/users/auth")
    fun login(@Body body: LoginRequest):Call<LoginResponse>

    @POST("/users")
    fun save(@Body body: SaveUsers): Call<LoginResponse>

    @GET("/users/{id}/match")
    fun findMatchList(
        @Path("id") id: Long,
        @Query("maxDistance") maxDistance: Int
    ): Call<MutableList<UserMatch>>

    @POST("/{id}/likeUser/{likedId}")
    fun likeUser(
        @Path("id") id: Long,
        @Path("likedId") likedId: Long
    ): Call<ResponseBody>

}