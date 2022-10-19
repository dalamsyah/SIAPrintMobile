package com.dalamsyah.siaprint.models

data class SizeData(
    var size_code: String = "",
    var size_name: String = "",
    var size_detail: String = "",
    var size_text: String = "",
    var active: String = "",
    var weight: String = "",
    var uom: String = "",
    var ink_code: String = ""
)

data class PriceFinishData(
    var price_finish_code: String = "",
    var size_code: String = "",
    var finish_code: String = "",
    var finish_text: String = "",
    var price: String = ""
)

data class PriceData(
    var price_code: String = "",
    var size_code: String = "",
    var ink_code: String = "",
    var type_paper_code: String = "",
    var price: String = "",
    var size_name: String = "",
    var ink_name: String = "",
    var type_paper_name: String = ""
)

data class Print(
    var size_data: MutableList<SizeData>?,
    var weight_data: String = "",
    var weightfinish_data: String = "",
    var pricefinish_data: MutableList<PriceFinishData>?,
    var price_data: MutableList<PriceData>?,
    var copy: String = "1"
)

data class PrintArray(
    var check_ink: String = "",
    var id_papper: String = "",
    var type_paper: String = "",
    var pages: String = "",
    var count_print: String = "",
    var finishing: String = "",
    var remarks: String = "",
    var fn_temp: String = "",
    var fn_filename: String = "",
    var fn_filename_random: String = "",
    var fn_size_code: String = "",
    var fn_ink_code: String = "",
    var fn_type_paper_code: String = "",
    var fn_copy: String = "",
    var fn_finish_code: String = "",
    var fn_pages: String = "",
    var fn_amount: String = "",
    var fn_price: String = "",
    var fn_price_finish: String = "",
    var fn_weigth: String = "",
    var fn_weigth_finish: String = "",
    var fn_weigth_item: String = "",
    var sub_total: Int = 0
)

data class PrintSave(
    var csrf_test_name: String = "",
    var fn_addr_default_regencies_id: String = "",
    var fn_addr_default_provinces_id: String = "",
    var fn_addr_default_address: String = "",
    var fn_addr_default_regencies_name: String = "",
    var fn_addr_default_provinces_name: String = "",
    var fn_addr_default_postcode: String = "",
    var fn_addr_default_phone: String = "",
    var fn_addr_default_receiver: String = "",
    var fn_total_amount2: String = "",
    var fn_total_amount: String = "",
    var fn_total_weigth: String = "",
    var total_row: String = "",
    var company_id: String = "",
    var company_provinces_id: String = "",
    var company_regencies_id: String = "",
    var fn_total_delv: String = "",
    var fn_delv_info: String = "",
    var fn_delv: String = "",
    var delv: String = "",
    var arr_detail: MutableList<PrintArray> = mutableListOf(),
    var basket: Basket = Basket(),
    var baskets: MutableList<Basket> = mutableListOf()
)

data class MethodDelivery(
    var methodCode: String = "",
    var methodName: String = "",
    var methodPrice: String = ""
)