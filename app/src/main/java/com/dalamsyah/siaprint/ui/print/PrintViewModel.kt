package com.dalamsyah.siaprint.ui.print

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dalamsyah.siaprint.models.*
import com.dalamsyah.siaprint.utils.toObject
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class PrintViewModel : ViewModel() {

    init {
    }

    private val _listPrintDetail = MutableLiveData<Print>()
    val listPrintDetail
        get() = _listPrintDetail

    /**
     * method delivery
     */
    var methodDelivery = MethodDelivery()

    /**
     * ink code selected
     */
    var ink_code = ""

    /**
     * price data code selected
     */
    var price_code = ""

    /**
     * copy field
     */
//    var copy = "1"
    private val _copy = MutableLiveData<String>()
    val copy
        get() = _copy

    /**
     * list all ukuran kertas
     */
    val listUkuranKertas = mutableListOf<SizeData>()

    /**
     * list selected ukuran kertas
     */
    val listUkuranKertasSelected = mutableListOf<SizeData>()

    /**
     * list all jenis kertas
     */
    val listJenisKertas = mutableListOf<PriceData>()

    /**
     * list selected jenis kertas
     */
    val listJenisKertasSelected = mutableListOf<PriceData>()

    /**
     * current jenis kertas
     */
    var jenisKertasCurrent = PriceData()

    /**
     * list all finishing
     */
    val listFinishing = mutableListOf<PriceFinishData>()

    /**
     * list selected finishing
     */
    val listFinishingSelected = mutableListOf<PriceFinishData>()

    /**
     * current finishing print
     */
    var finishingPrice = PriceFinishData()

    /**
     * grand total print
     */
    private val _grangTotal = MutableLiveData<Int>()
    val grangTotal
        get() = _grangTotal

    fun setCopy(s: String) {
       _copy.value = s
    }

    fun generateGrandTotal(){

        var total = 0

        try {
            total += jenisKertasCurrent.price.toInt()
        } catch (e: Exception){

        }
        try {
            total += finishingPrice.price.toInt()
        } catch (e: Exception){

        }

        if (copy.value == ""){
            setCopy("1")
        }

        _grangTotal.value = total * _copy.value!!.toInt()
    }

    fun filterListFinishing(size_code: String, price_code: String){
        listFinishingSelected.clear()
        for ( model in listFinishing ){
            if (model.size_code == size_code ){ //&& model.price_finish_code == price_code
                Logger.d("filterListFinishing")
                listFinishingSelected.add( model )
            }
        }
    }

    /**
     * filter jenis kertas by @param ink_code @param size_code print
     */
    fun filterJenisKertas(ink_code: String, size_code: String){
        listJenisKertasSelected.clear()
        for (model in listJenisKertas){
            if ( model.ink_code == ink_code && model.size_code == size_code){
                listJenisKertasSelected.add( model )
            }
        }
    }

    /**
     * filter ukuran kertas by @param ink_code print
     */
    fun filterUkuranKertas(ink_code: String){
        listUkuranKertasSelected.clear()
        for (sizedata in listUkuranKertas){
            if ( sizedata.ink_code == ink_code ){
                listUkuranKertasSelected.add( sizedata )
            }
        }
    }

    fun setPrintDetail(print: Print){
        _listPrintDetail.value = print
    }

    fun printSave(
            userid: String,
            basket: MutableList<Basket>,
            fn_total_amount: String,
            company_id: String,
            company_provinces_id: String,
            company_regencies_id: String,
            fn_total_delv: String,
            fn_delv_info: String,
            fn_delv: String
        ): JSONObject {

        var json = JSONObject()
        json.put("userid", userid)
        json.put("csrf_test_name", "")
        json.put("fn_addr_default_regencies_id", "")
        json.put("fn_addr_default_provinces_id", "")
        json.put("fn_addr_default_address", "")
        json.put("fn_addr_default_regencies_name", "")
        json.put("fn_addr_default_provinces_name", "")
        json.put("fn_addr_default_postcode", "")
        json.put("fn_addr_default_phone", "")
        json.put("fn_addr_default_receiver", "")
        json.put("fn_total_amount2", fn_total_amount)
        json.put("fn_total_amount", fn_total_amount)
        json.put("fn_total_weigth", "")
        json.put("total_row", "1")
        json.put("company_id", company_id)
        json.put("company_provinces_id", company_provinces_id)
        json.put("company_regencies_id", company_regencies_id)
        json.put("fn_total_delv", fn_total_delv)
        json.put("fn_delv_info", fn_delv_info)
        json.put("fn_delv", fn_delv)
        json.put("delv", fn_delv)

        for ((i, b) in basket.withIndex()){

            var model = b.printArray

            json.put("check_ink_$i", model.check_ink)
            json.put("id_papper_$i", model.id_papper)
            json.put("type_paper_$i", model.type_paper)
            json.put("pages_$i", model.pages)
            json.put("count_print_$i", model.count_print)
            json.put("finishing_$i", model.finishing)
            json.put("remarks_$i", model.remarks)
            json.put("fn_temp_$i", b.id)
            json.put("fn_filename_$i", model.fn_filename)
            json.put("fn_filename_random_$i", model.fn_filename_random)
            json.put("fn_size_code_$i", model.fn_size_code)
            json.put("fn_ink_code_$i", model.fn_ink_code)
            json.put("fn_type_paper_code_$i", model.fn_type_paper_code)
            json.put("fn_copy_$i", model.fn_copy)
            json.put("fn_finish_code_$i", model.fn_finish_code)
            json.put("fn_pages_$i", model.fn_pages)
            json.put("fn_amount_$i", model.fn_amount)
            json.put("fn_price_$i", model.fn_price)
            json.put("fn_price_finish_$i", model.fn_price_finish)
            json.put("fn_weigth_$i", model.fn_weigth)
            json.put("fn_weigth_finish_$i", model.fn_weigth_finish)
            json.put("fn_weigth_item_$i", model.fn_weigth_item)

        }

        Logger.d(json)

        return json
    }

}