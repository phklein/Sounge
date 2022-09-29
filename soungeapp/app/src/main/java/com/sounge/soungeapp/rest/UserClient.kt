package com.sounge.soungeapp.rest

import com.sounge.soungeapp.request.UpdateProfile
import com.sounge.soungeapp.request.UpdateRole
import com.sounge.soungeapp.response.UserPage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserClient {
    @GET("/users/{id}")
    fun getUserPage(
        @Path("id") id: Long,
        @Query("viewerId") viewerId: Long
    ): Call<UserPage>

    @PUT("/users/{id}")
    fun updateProfile(
        @Path("id") id: Long,
        @Body body: UpdateProfile
    ): Call<ResponseBody>

    @PUT("/users/{id}/roles/multiple")
    @JvmSuppressWildcards
    fun updateTalents(
        @Path("id") id: Long,
        @Body body: UpdateRole
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

    @POST("/users/{id}/likeComment/{commentId}")
    fun likeComment(
        @Path("id") id: Long,
        @Path("commentId") commentId: Long,
    ): Call<ResponseBody>

    @DELETE("/users/{id}/likeComment/{commentId}")
    fun unlikeComment(
        @Path("id") id: Long,
        @Path("commentId") commentId: Long,
    ): Call<ResponseBody>
}