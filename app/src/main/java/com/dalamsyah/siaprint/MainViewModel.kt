package com.dalamsyah.siaprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dalamsyah.siaprint.models.Basket
import com.dalamsyah.siaprint.models.Company
import com.dalamsyah.siaprint.models.PrintSave
import com.dalamsyah.siaprint.models.Users
import com.dalamsyah.siaprint.utils.convertRupiah

class MainViewModel : ViewModel() {

    /**
    * for showing loading bar
    * */
    private val _progressBar = MutableLiveData<Boolean>()
    val progressBarLayout: LiveData<Boolean> get() = _progressBar

    /**
    * for showing popup dialog
    * */
    private val _dialogLayout = MutableLiveData<Boolean>()
    val dialogLayout: LiveData<Boolean> get() = _dialogLayout

    /**
    * for showing type dialog konfirmasi
    * */
    private val _dialogKonfirmasi = MutableLiveData<Boolean>()
    val dialogKonfirmasi: LiveData<Boolean> get() = _dialogKonfirmasi


    /**
    * for set msg dialog
    * */
    private val _dialogMsg = MutableLiveData<String>()
    val dialogMsg: LiveData<String> get() = _dialogMsg

    /**
    * for kondisi button aksi OK
    * */
    var isActionButtonDialogOK = false

    private val _user = MutableLiveData<Users>()
    val user: LiveData<Users> get() = _user

    /**
    * for print selected on page keranjang
    * */
    private val _printSelected = MutableLiveData<MutableList<Basket>>()
    val printSelected: LiveData<MutableList<Basket>> get() = _printSelected

    /**
     * for object to convert JSON for post Print
     */
    private val _printSave = MutableLiveData<PrintSave>()
    val printSave: LiveData<PrintSave> get() = _printSave

    /**
    * for get object Company
    * */
    var companySelected = Company()

    /**
     * global Grand Total
     */
    var grandTotal = 0

    private val _grandTotalText = MutableLiveData<String>()
    val grandTotalText
        get() = _grandTotalText

    fun replacePrintSelected(basket: Basket){
        _printSelected.value?.remove( basket )
        _printSelected.value?.add( basket )
    }

    fun setPrintSave(printSave: PrintSave){
        _printSave.value = printSave
    }

    fun setPrintSelected(list: MutableList<Basket>){
        _printSelected.value = list
    }

    fun setUser(users: Users?){
        _user.value = users
    }

    fun showProgress(show: Boolean){
        _progressBar.value = show
    }

    fun showDialog(msg: String, konf: Boolean, isAction: Boolean = false){
        _dialogLayout.value = true
        _dialogMsg.value = msg
        _dialogKonfirmasi.value = konf
        isActionButtonDialogOK = isAction
    }

    fun hideDialog(){
        _dialogLayout.value = false
    }

}