package com.dalamsyah.siaprint.retrofit

import com.dalamsyah.siaprint.models.ResponseAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
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

    @POST("apibasket")
    @FormUrlEncoded
    suspend fun keranjang(@Field("apitoken") apitoken: String,
                       @Field("userid") userid: String): ResponseAPI

    @POST("apiprint")
    @FormUrlEncoded
    suspend fun printDetail(@Field("apitoken") apitoken: String,
                          @Field("compid") compid: String,
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

    @POST("apibasketsave")
    @FormUrlEncoded
    suspend fun printSave(@Field("apitoken") apitoken: String,
                          @Field("userid") userid: String,
                          @Field("data2") arr: JSONObject,
                          @Body data: JSONObject  ): ResponseAPI

    @POST("apibasketsave")
    @Headers( "Content-Type: application/json; charset=utf-8")
    suspend fun printSave2( @Body data: JSONObject  ): ResponseAPI

    @Multipart
    @POST("apiuploadsave")
    suspend fun upload(

        @Part inputFile1: MultipartBody.Part,

        @Part inputFile2: MultipartBody.Part,

        @Part inputFile3: MultipartBody.Part,

        @Part inputFile4: MultipartBody.Part,

        @Part inputFile5: MultipartBody.Part
    )

    @Multipart
    @POST("apiuploadsave")
    suspend fun uploadSingle(

        @Part("apitoken") apitoken: String,
        @Part("userid") userid: String,

        @Part inputFile1: MultipartBody.Part,
        @Part("desc") desc: RequestBody
    ): ResponseAPI

    @POST("apiongkir")
    @Headers( "Content-Type: application/json; charset=utf-8")
    suspend fun ongkir( @Body data: JSONObject  ): ResponseAPI

}