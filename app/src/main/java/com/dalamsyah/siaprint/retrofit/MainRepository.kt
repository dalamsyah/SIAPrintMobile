package com.dalamsyah.siaprint.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(username: String, password: String) = apiHelper.login(username, password)
    suspend fun register(email: String, password: String, pass_confirm: String, username: String) = apiHelper.register(email, password, pass_confirm, username)
    suspend fun status(apitoken: String, userid: String) = apiHelper.status(apitoken, userid)
    suspend fun keranjang(apitoken: String, userid: String) = apiHelper.keranjang(apitoken, userid)
    suspend fun payment(apitoken: String, payment_no: String, total_amount: String, payment_type: String, payment_name: String, phone_no: String, vendor_code: String) =
        apiHelper.payment(apitoken, payment_no, total_amount, payment_type, payment_name, phone_no, vendor_code)
    suspend fun upload(apitoken: String, userid: String,
                       inputFile1: MultipartBody.Part, inputFile2: MultipartBody.Part, inputFile3: MultipartBody.Part,
                       inputFile4: MultipartBody.Part, inputFile5: MultipartBody.Part,
                       desc: RequestBody
    ) =
        apiHelper.upload(apitoken, userid, inputFile1, inputFile2, inputFile3, inputFile4, inputFile5, desc)

    suspend fun printDetail(apitoken: String, compid: String, userid: String) = apiHelper.printDetail(apitoken, compid, userid)
    suspend fun printSave(apitoken: String, userid: String, arr: JSONObject, data: JSONObject) = apiHelper.printSave(apitoken, userid, arr, data)
    suspend fun printSave2(apitoken: String, userid: String, arr: JSONObject, data: JSONObject) = apiHelper.printSave2(apitoken, userid, arr, data)
    suspend fun ongkir(data: JSONObject) = apiHelper.ongkir(data)

}