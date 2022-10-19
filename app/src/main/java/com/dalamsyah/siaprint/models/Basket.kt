package com.dalamsyah.siaprint.models

import java.io.Serializable

data class Basket (
    var id: String = "",
    var user_id: String = "",
    var filename: String = "",
    var pages_tot: String = "",
    var slug: String = "",
    var filename_random: String = "",
    var created_at: String = "",
    var updated_at: String = "",
    var deleted_at: String = "",
    var selected: Boolean = false,
    var isComplete: Boolean = false,
    var printArray: PrintArray = PrintArray()
) : Serializable
