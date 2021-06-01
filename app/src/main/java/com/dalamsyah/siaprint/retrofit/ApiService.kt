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

    @POST("apipayment")
    @FormUrlEncoded
    suspend fun payment(@Field("apitoken") apitoken: String,
                        @Field("payment_no") payment_no: String,
                        @Field("total_amount") total_amount: String,
                        @Field("payment_type") payment_type: String,
                        @Field("payment_name") payment_name: String,
                        @Field("phone_no") phone_no: String,
                        @Field("vendor_code") vendor_code: String): ResponseAPI
}