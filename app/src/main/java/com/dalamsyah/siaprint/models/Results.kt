package com.dalamsyah.siaprint.models

import com.google.gson.annotations.SerializedName

data class Results (
    @SerializedName("user")
    var user: Users,

    @SerializedName("trsc_print_h")
    var trsc_print_h: MutableList<TransactionPrintH>,

    @SerializedName("trsc_print_d")
    var trsc_print_d: MutableList<TransactionPrintD>,

    @SerializedName("payment")
    var payment: Payment
)
