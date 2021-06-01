package com.dalamsyah.siaprint.models

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("amount")
    var amount: Int = 0,

    @SerializedName("business_id")
    var business_id: String = "",

    @SerializedName("ewallet_type")
    var ewallet_type: String = "",

    @SerializedName("external_id")
    var external_id: String = "",

    @SerializedName("phone")
    var phone: String = "",

    @SerializedName("transaction_date")
    var transaction_date: String = "",

    @SerializedName("ewallet_transaction_id")
    var ewallet_transaction_id: String = ""

)
