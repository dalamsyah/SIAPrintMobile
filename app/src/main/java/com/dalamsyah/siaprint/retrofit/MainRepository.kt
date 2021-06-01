package com.dalamsyah.siaprint.retrofit

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(username: String, password: String) = apiHelper.login(username, password)
    suspend fun register(email: String, password: String, pass_confirm: String, username: String) = apiHelper.register(email, password, pass_confirm, username)
    suspend fun status(apitoken: String, userid: String) = apiHelper.status(apitoken, userid)

}