package com.dalamsyah.siaprint.ui.status

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.BuildConfig
import com.dalamsyah.siaprint.MainActivity
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentRegisterBinding
import com.dalamsyah.siaprint.databinding.FragmentStatusBinding
import com.dalamsyah.siaprint.models.ResponseAPI
import com.dalamsyah.siaprint.retrofit.Status
import com.dalamsyah.siaprint.ui.register.RegisterViewModel
import com.dalamsyah.siaprint.ui.upload.UploadAdapter
import com.dalamsyah.siaprint.ui.upload.UploadListener
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.dalamsyah.siaprint.utils.convertRupiah
import com.dalamsyah.siaprint.utils.printLog
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import org.json.JSONObject

class StatusFragment : BaseFragment() {

    private val viewModel: StatusViewModel by activityViewModels()
    private var _binding: FragmentStatusBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StatusAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        viewModel = ViewModelProvider(this).get(StatusViewModel::class.java)

        _binding = FragmentStatusBinding.inflate(inflater, container, false)

        adapter = StatusAdapter( StatusListener { model ->
            val bundle = Bundle().apply {
                putSerializable("trans_h", model)
            }

            findNavController().navigate(R.id.action_statusFragment_to_statusDetailFragment, bundle)
        })
        binding.recyclerView.adapter = adapter

        getStatus()

        viewModel.listTransH.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getStatus(){
        viewModelAPI.status(pref.apiToken, pref.user?.id.toString()).observe(viewLifecycleOwner, {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        mainViewModel.showProgress(true)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.showProgress(false)

                        val a = 1000

                        it.data?.let { api ->

//                            val json = Gson().toJson(api, ResponseAPI::class.java)
//                            val data = Gson().fromJson(json, ResponseAPI::class.java)

                            viewModel.setTransH( api.result!!.trsc_print_h )
                            viewModel.setTransD( api.result!!.trsc_print_d )

                            Logger.d( api.result )

                            if (api.result != null) {

                            } else {

                            }
                        }
                    }
                    Status.ERROR -> {
                        if (BuildConfig.DEBUG){
                            mainViewModel.showDialog("${it.message}", false )
                        } else {
                            mainViewModel.showDialog(resources.getString(R.string.error_api), false )
                        }
                        mainViewModel.showProgress(false)
                    }
                }
            }
        })
    }

}