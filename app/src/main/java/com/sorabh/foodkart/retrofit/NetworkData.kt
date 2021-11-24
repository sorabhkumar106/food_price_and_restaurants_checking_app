package com.sorabh.foodkart.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkData {
   private val baseUrl = "http://13.235.250.119/v2/restaurants/"
    fun getNetworkAccess(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}