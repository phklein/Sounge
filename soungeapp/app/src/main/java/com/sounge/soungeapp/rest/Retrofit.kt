package com.sounge.soungeapp.rest

import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Retrofit {
    private const val BASE_URL = "http://10.0.2.2:8082/"
//    private const val BASE_URL = "http://192.168.17.106:8082"
    val baseApiIBGE = "https://servicodados.ibge.gov.br"

    fun getInstance() : Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = Builder()
            .addInterceptor(interceptor)
            .callTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getInstanceIBGE(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseApiIBGE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}