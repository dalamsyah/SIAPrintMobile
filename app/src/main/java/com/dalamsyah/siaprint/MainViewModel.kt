package com.dalamsyah.siaprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dalamsyah.siaprint.models.Users

class MainViewModel : ViewModel() {

    /*
    * for showing loading bar
    * */
    private val _progressBar = MutableLiveData<Boolean>()
    val progressBarLayout: LiveData<Boolean> get() = _progressBar

    /*
    * for showing popup dialog
    * */
    private val _dialogLayout = MutableLiveData<Boolean>()
    val dialogLayout: LiveData<Boolean> get() = _dialogLayout

    /*
    * for showing type dialog konfirmasi
    * */
    private val _dialogKonfirmasi = MutableLiveData<Boolean>()
    val dialogKonfirmasi: LiveData<Boolean> get() = _dialogKonfirmasi


    /*
    * for set msg dialog
    * */
    private val _dialogMsg = MutableLiveData<String>()
    val dialogMsg: LiveData<String> get() = _dialogMsg

    /*
    * for kondisi button aksi OK
    * */
    var isActionButtonDialogOK = false

    private val _user = MutableLiveData<Users>()
    val user: LiveData<Users> get() = _user

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