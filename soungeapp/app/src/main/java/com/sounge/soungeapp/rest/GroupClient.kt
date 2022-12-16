package com.sounge.soungeapp.rest

import com.sounge.soungeapp.response.GroupMatch
import com.sounge.soungeapp.response.Page
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupClient {
    @GET("/groups/match")
    fun findGroupMatchList(
        @Query("userId") userId: Long,
        @Query("maxDistance") maxDistance: Int,
        @Query("page") page: Int
    ): Call<Page<GroupMatch>>
}