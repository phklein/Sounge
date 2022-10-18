package com.sounge.soungeapp.services

import com.sounge.soungeapp.models.States
import retrofit2.Call
import retrofit2.http.GET

interface StatesService {

    @GET("/api/v1/localidades/estados")
    fun getStates(): Call<List<States>>
}