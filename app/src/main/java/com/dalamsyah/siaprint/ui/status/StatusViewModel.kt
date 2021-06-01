package com.dalamsyah.siaprint.ui.status

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dalamsyah.siaprint.models.TransactionPrintD
import com.dalamsyah.siaprint.models.TransactionPrintH
import com.dalamsyah.siaprint.models.Upload
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatusViewModel : ViewModel() {
    private val _listTransH = MutableLiveData<MutableList<TransactionPrintH>>()
    val listTransH
        get() = _listTransH

    private val _listTransD = MutableLiveData<MutableList<TransactionPrintD>>()
    val listTransD
        get() = _listTransD

    private val _listTransDSelected = MutableLiveData<MutableList<TransactionPrintD>>()
    val listTransDSelected
        get() = _listTransDSelected

    fun setTransH(list: MutableList<TransactionPrintH>){
        _listTransH.value = list
    }

    fun setTransD(list: MutableList<TransactionPrintD>){
        _listTransD.value = list
    }

    private fun setTransDSelected(list: TransactionPrintD){
        _listTransDSelected.value?.add( list )
    }

    private fun clearTransDSelected(){
        _listTransDSelected.value?.clear()
    }

    fun filterById(trans_h: TransactionPrintH, list: MutableList<TransactionPrintD>){

        clearTransDSelected()

        if (list.isNotEmpty()){

            _listTransDSelected.value = mutableListOf()

            viewModelScope.launch(Dispatchers.IO) {

                for( model in list ){
                    if (model.print_h_code.equals( trans_h.print_h_code )){
                        Logger.d(trans_h)
                        Logger.d(model)
                        setTransDSelected( model )
                    }
                }

            }
        }

    }

}