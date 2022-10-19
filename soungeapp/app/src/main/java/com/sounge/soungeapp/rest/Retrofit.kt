package com.sounge.soungeapp.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE_URL = "http://10.0.2.2:8082/"
    val baseApiIBGE = "https://servicodados.ibge.gov.br"

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstanceIBGE(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseApiIBGE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}