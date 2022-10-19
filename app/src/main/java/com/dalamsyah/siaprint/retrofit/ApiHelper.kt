package com.dalamsyah.siaprint.retrofit

import com.dalamsyah.siaprint.models.Users
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

class ApiHelper(private val apiService: ApiService, private val user: Users?) {

    suspend fun login(username: String, password: String) = apiService.login(username, password)
    suspend fun register(email: String, password: String, pass_confirm: String, username: String) = apiService.register(email, password, pass_confirm, username)
    suspend fun status(apitoken: String, userid: String) = apiService.status(apitoken, userid)
    suspend fun keranjang(apitoken: String, userid: String) = apiService.keranjang(apitoken, userid)
    suspend fun payment(apitoken: String, payment_no: String, total_amount: String, payment_type: String, payment_name: String, phone_no: String, vendor_code: String) =
        apiService.payment(apitoken, payment_no, total_amount, payment_type, payment_name, phone_no, vendor_code)
    suspend fun printSave(apitoken: String, userid: String, arr: JSONObject, data: JSONObject) = apiService.printSave(apitoken, userid, arr, data)
    suspend fun printSave2(apitoken: String, userid: String, arr: JSONObject, data: JSONObject) = apiService.printSave2(data)
    suspend fun ongkir(data: JSONObject) = apiService.ongkir(data)

    suspend fun upload(
        apitoken: String, userid: String,
        inputFile1: MultipartBody.Part, inputFile2: MultipartBody.Part, inputFile3: MultipartBody.Part,
        inputFile4: MultipartBody.Part, inputFile5: MultipartBody.Part,
        desc: RequestBody
    ) = apiService.uploadSingle(apitoken, userid, inputFile1, desc)

    suspend fun printDetail(apitoken: String, compid: String, userid: String) = apiService.printDetail(apitoken, compid, userid)


}