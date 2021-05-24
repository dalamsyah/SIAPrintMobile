package com.dalamsyah.siaprint.ui.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import com.dalamsyah.siaprint.MainViewModel
import com.dalamsyah.siaprint.retrofit.APIFactoryViewModel
import com.dalamsyah.siaprint.retrofit.APIViewModel
import com.dalamsyah.siaprint.retrofit.ApiHelper
import com.dalamsyah.siaprint.retrofit.RetrofitBuilder
import com.dalamsyah.siaprint.storage.Prefs
import com.dalamsyah.siaprint.ui.gallery.GalleryViewModel

open class BaseFragment : Fragment() {

    lateinit var viewModelAPI: APIViewModel

    val mainViewModel: MainViewModel by activityViewModels()
    lateinit var pref: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = Prefs(context!!)

        viewModelAPI = activity?.let { ViewModelProviders.of(it, APIFactoryViewModel(
            ApiHelper( RetrofitBuilder.apiService, user = null )
        )
        ).get(
            APIViewModel::class.java)
        }!!
    }

}