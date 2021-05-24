package com.dalamsyah.siaprint.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class APIFactoryViewModel(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(APIViewModel::class.java)) {
            return APIViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}