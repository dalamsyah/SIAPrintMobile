package com.dalamsyah.siaprint.ui.keranjang

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dalamsyah.siaprint.models.Basket
import com.dalamsyah.siaprint.models.Company
import com.dalamsyah.siaprint.models.TransactionPrintD
import com.dalamsyah.siaprint.models.TransactionPrintH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeranjangViewModel : ViewModel() {

    private val _listBasket = MutableLiveData<MutableList<Basket>>()
    val listBasket
        get() = _listBasket

    private val _listCompanySelected = MutableLiveData<MutableList<Company>>()
    val listCompanySelected
        get() = _listCompanySelected

    private val _isEnableBtnNext = MutableLiveData<Boolean>()
    val isEnableBtnNext
        get() = _isEnableBtnNext

    fun setListBasket(list: MutableList<Basket>){
        _listBasket.value = list
    }

    fun clearListBasket(){
        _listBasket.value?.clear()
    }

    fun setListCompanySelected(list: MutableList<Company>){
        _listCompanySelected.value = list
    }

    fun clearListCompanySelected(){
        _listCompanySelected.value?.clear()
    }

    fun checkSelected(list: MutableList<Basket>){
        viewModelScope.launch(Dispatchers.Main) {
            _isEnableBtnNext.value = false
            for (model in list){
                if (model.selected){
                    _isEnableBtnNext.value = true
                    break
                }
            }
        }
    }

}