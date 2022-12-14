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
    var payment: Payment,

    @SerializedName("basket")
    var basket: MutableList<Basket>,

    @SerializedName("company")
    var company: MutableList<Company>,

    @SerializedName("company_selected")
    var company_selected: MutableList<Company>,

    @SerializedName("print")
    var print: Print
)
