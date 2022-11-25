package com.sounge.soungeapp.services

import com.sounge.soungeapp.models.Town
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TownService {

    @GET("/api/v1/localidades/estados/{UF}/municipios")
    fun getTown(@Path("UF") uf: Int): Call<List<Town>>
}