package com.dalamsyah.siaprint.models

import com.google.gson.annotations.SerializedName

data class ResultErrorLogin (
    @SerializedName("login")
    var login: String,

    @SerializedName("password")
    var password: String
)