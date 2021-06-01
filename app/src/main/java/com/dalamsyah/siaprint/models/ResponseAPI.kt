package com.dalamsyah.siaprint.models

import com.google.gson.annotations.SerializedName
import java.util.*

class ResponseAPI(
    @SerializedName("message")
    var message: Any? = null,

    @SerializedName("status")
    var status: Int? = null,

    @SerializedName("code")
    var code: Int? = null,

    @SerializedName("result")
    var result: Results? = null
)
