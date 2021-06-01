package com.dalamsyah.siaprint.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TransactionPrintH(

    @SerializedName("print_h_code")
    var print_h_code: String? = null,

    @SerializedName("created_at")
    var created_at: String? = null,

    @SerializedName("amount_h")
    var amount_h: String? = null,

    @SerializedName("trsc_h_status")
    var trsc_h_status: String? = null,

    @SerializedName("trsc_h_status_name")
    var trsc_h_status_name: String? = null,

    @SerializedName("trsc_h_status_text")
    var trsc_h_status_text: String? = null,

    @SerializedName("delv_code")
    var delv_code: String? = null,

    @SerializedName("delv_name")
    var delv_name: String? = null,

    @SerializedName("company_name")
    var company_name: String? = null,

    @SerializedName("provinces_name")
    var provinces_name: String? = null,

    @SerializedName("regencies_name")
    var regencies_name: String? = null,

    @SerializedName("comp_address")
    var comp_address: String? = null,

    @SerializedName("ntgew_h")
    var ntgew_h: String? = null,

    @SerializedName("ntgew_uom_h")
    var ntgew_uom_h: String? = null,

    @SerializedName("delv_cost")
    var delv_cost: String? = null,

    @SerializedName("amount_p")
    var amount_p: String? = null,

    @SerializedName("delv_text")
    var delv_text: String? = null,

    @SerializedName("shipp_receiver")
    var shipp_receiver: String? = null,

    @SerializedName("shipp_phone")
    var shipp_phone: String? = null,

    @SerializedName("shipp_address")
    var shipp_address: String? = null,

    @SerializedName("shipp_postcode")
    var shipp_postcode: String? = null,

    @SerializedName("delv_text2")
    var delv_text2: String? = null
) : Serializable

data class TransactionPrintD (

    @SerializedName("print_h_code")
    var print_h_code: String? = null,

    @SerializedName("created_at")
    var created_at: String? = null,

    @SerializedName("amount_h")
    var amount_h: String? = null,

    @SerializedName("trsc_h_status")
    var trsc_h_status: String? = null,

    @SerializedName("trsc_h_status_name")
    var trsc_h_status_name: String? = null,

    @SerializedName("trsc_h_status_text")
    var trsc_h_status_text: String? = null,

    @SerializedName("delv_code")
    var delv_code: String? = null,

    @SerializedName("delv_name")
    var delv_name: String? = null,

    @SerializedName("size_code")
    var size_code: String? = null,

    @SerializedName("size_name")
    var size_name: String? = null,

    @SerializedName("ink_code")
    var ink_code: String? = null,

    @SerializedName("ink_name")
    var ink_name: String? = null,

    @SerializedName("type_paper_code")
    var type_paper_code: String? = null,

    @SerializedName("type_paper_name")
    var type_paper_name: String? = null,

    @SerializedName("finish_code")
    var finish_code: String? = null,

    @SerializedName("finish_text")
    var finish_text: String? = null,

    @SerializedName("total_pages")
    var total_pages: String? = null,

    @SerializedName("pages_remarks")
    var pages_remarks: String? = null,

    @SerializedName("amount_d")
    var amount_d: String? = null,

    @SerializedName("price")
    var price: String? = null,

    @SerializedName("price_finish")
    var price_finish: String? = null,

    @SerializedName("filename")
    var filename: String? = null,

    @SerializedName("filename_random")
    var filename_random: String? = null,

    @SerializedName("copy")
    var copy: String? = null,

    @SerializedName("remarks_d")
    var remarks_d: String? = null,

    @SerializedName("ntgew_h")
    var ntgew_h: String? = null,

    @SerializedName("ntgew_uom_h")
    var ntgew_uom_h: String? = null,

    @SerializedName("ntgew_d")
    var ntgew_d: String? = null,

    @SerializedName("ntgew_uom_d")
    var ntgew_uom_d: String? = null,
)
