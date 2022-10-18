package com.sounge.soungeapp.rest

import com.sounge.soungeapp.data.LoginRequest
import com.sounge.soungeapp.data.LoginResponse
import com.sounge.soungeapp.data.SaveUsers
import com.sounge.soungeapp.data.UserPage
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


}