package com.dalamsyah.siaprint.models

data class Company (
    var comp_id: String = "",
    var comp_name: String = "",
    var comp_address: String = "",
    var comp_text1: String = "",
    var provinces_id: String = "",
    var provinces_name: String = "",
    var regencies_id: String = "",
    var regencies_name: String = "",
    var price_status: String = "",
    var price_finish_status: String = ""
)