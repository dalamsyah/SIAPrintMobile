package com.dalamsyah.siaprint.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

//    private const val BASE_URL = "http://192.168.1.4/api_lumen/public/"
    private const val BASE_URL = "http://davdivdev.siaprint.com/"
//    const val BASE_URL = "http://192.168.43.236/project/fitgo_api/public/"
//    const val BASE_URL = "http://skripsiangga.xyz/fitgo/public/"
//    const val BASE_URL = "https://apifitgo.dalamsyah.com/"

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build() //Doesn't require the adapter
    }

    private fun getRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
    fun apiServiceWithUrl(url: String): ApiService = getRetrofit(url).create(ApiService::class.java)

}