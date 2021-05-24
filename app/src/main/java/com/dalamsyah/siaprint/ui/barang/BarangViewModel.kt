package com.dalamsyah.siaprint.ui.barang

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BarangViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Barang Fragment"
    }
    val text: LiveData<String> = _text
}