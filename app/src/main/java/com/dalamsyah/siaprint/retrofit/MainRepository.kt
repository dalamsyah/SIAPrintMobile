package com.dalamsyah.siaprint.retrofit

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(username: String, password: String) = apiHelper.login(username, password)
    suspend fun register(email: String, password: String, pass_confirm: String, username: String) = apiHelper.register(email, password, pass_confirm, username)
    suspend fun status(apitoken: String, userid: String) = apiHelper.status(apitoken, userid)
    suspend fun payment(apitoken: String, payment_no: String, total_amount: String, payment_type: String, payment_name: String, phone_no: String, vendor_code: String) =
        apiHelper.payment(apitoken, payment_no, total_amount, payment_type, payment_name, phone_no, vendor_code)

}