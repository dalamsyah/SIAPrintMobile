package com.dalamsyah.siaprint.ui.index

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IndexViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

}