package com.dalamsyah.siaprint.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dalamsyah.siaprint.models.Upload

class UploadViewModel : ViewModel() {

    private val _listUpload = MutableLiveData<MutableList<Upload>>()
    val listUpload
        get() = _listUpload

}