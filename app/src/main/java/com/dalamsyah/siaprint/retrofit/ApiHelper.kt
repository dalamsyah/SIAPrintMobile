package com.dalamsyah.siaprint.retrofit

import com.dalamsyah.siaprint.models.Users

class ApiHelper(private val apiService: ApiService, private val user: Users?) {

    suspend fun login(username: String, password: String) = apiService.login(username, password)
    suspend fun register(email: String, password: String, pass_confirm: String, username: String) = apiService.register(email, password, pass_confirm, username)

}