package com.sounge.soungeapp.rest

import com.sounge.soungeapp.data.UserPage
import retrofit2.Call
import retrofit2.http.GET


interface UserClient {
    @GET("/users/{id}")
    fun getProfile() : Call<UserPage>
}