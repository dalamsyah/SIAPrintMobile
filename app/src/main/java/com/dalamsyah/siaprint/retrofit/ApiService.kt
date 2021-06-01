package com.dalamsyah.siaprint.retrofit

import com.dalamsyah.siaprint.models.ResponseAPI
import retrofit2.http.*

interface ApiService {

    @POST("login-api")
    @FormUrlEncoded
    suspend fun login(@Field("login") username: String,
                      @Field("password") password: String): ResponseAPI

    @POST("register-api")
    @FormUrlEncoded
    suspend fun register(@Field("email") email: String,
                      @Field("password") password: String,
                      @Field("pass_confirm") pass_confirm: String,
                      @Field("username") username: String,
    ): ResponseAPI

    @POST("apistatus")
    @FormUrlEncoded
    suspend fun status(@Field("apitoken") apitoken: String,
                      @Field("userid") userid: String): ResponseAPI
}