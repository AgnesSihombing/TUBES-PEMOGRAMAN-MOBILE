package com.kampus.praktikumpemob

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("advice")
    suspend fun getAdvice(): AdviceResponse
}

//Background task
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.adviceslip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api by lazy{
        retrofit.create(ApiService::class.java)
    }
}