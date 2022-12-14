package com.sounge.soungeapp.rest

import com.sounge.soungeapp.request.UpdateGenres
import com.sounge.soungeapp.request.UpdateProfile
import com.sounge.soungeapp.request.UpdateRoles
import com.sounge.soungeapp.response.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserClient {
    @GET("/users/{id}")
    fun getUserPage(
        @Path("id") id: Long,
        @Query("viewerId") viewerId: Long
    ): Call<UserPage>

    @GET("/users/{id}/posts")
    fun getUserPosts(
        @Path("id") id: Long,
        @Query("viewerId") viewerId: Long,
        @Query("page") page: Int
    ): Call<Page<PostSimple>>

    @PUT("/users/{id}")
    fun updateProfile(
        @Path("id") id: Long,
        @Body body: UpdateProfile
    ): Call<ResponseBody>

    @PUT("/users/{id}/roles")
    fun updateTalents(
        @Path("id") id: Long,
        @Body body: UpdateRoles
    ): Call<ResponseBody>

    @PUT("/users/{id}/genres")
    fun updateGenres(
        @Path("id") id: Long,
        @Body body: UpdateGenres
    ): Call<ResponseBody>

    @POST("/users/{id}/likePost/{postId}")
    fun likePost(
        @Path("id") id: Long,
        @Path("postId") postId: Long
    ): Call<ResponseBody>

    @DELETE("/users/{id}/likePost/{postId}")
    fun unlikePost(
        @Path("id") id: Long,
        @Path("postId") postId: Long
    ): Call<ResponseBody>

    @POST("/users/{id}/likeComment/{commentId}")
    fun likeComment(
        @Path("id") id: Long,
        @Path("commentId") commentId: Long
    ): Call<ResponseBody>

    @DELETE("/users/{id}/likeComment/{commentId}")
    fun unlikeComment(
        @Path("id") id: Long,
        @Path("commentId") commentId: Long
    ): Call<ResponseBody>

    @POST("/users/auth")
    fun login(@Body body: LoginRequest):Call<LoginResponse>

    @POST("/users")
    fun save(@Body body: SaveUsers): Call<LoginResponse>

    @GET("/users/{id}/match")
    fun findMatchList(
        @Path("id") id: Long,
        @Query("maxDistance") maxDistance: Int,
        @Query("page") page: Int
    ): Call<MutableList<UserMatch>>

    @POST("/{id}/likeUser/{likedId}")
    fun likeUser(
        @Path("id") id: Long,
        @Path("likedId") likedId: Long
    ): Call<ResponseBody>

    @GET("/users/{id}/contacts/details")
    fun findContactDetails(
        @Path("id") id: Long
    ): Call<UserMatch>

    @GET("/users/{id}/contacts")
    fun findContactList(
        @Path("id") id: Long,
        @Query("page") page: Int
    ): Call<MutableList<UserContact>>

}