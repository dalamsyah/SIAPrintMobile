package com.dalamsyah.siaprint.ui.print

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.BuildConfig
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentPrintBinding
import com.dalamsyah.siaprint.databinding.FragmentPrintDetailBinding
import com.dalamsyah.siaprint.models.Basket
import com.dalamsyah.siaprint.models.MethodDelivery
import com.dalamsyah.siaprint.models.PrintSave
import com.dalamsyah.siaprint.retrofit.Status
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.dalamsyah.siaprint.utils.convertRupiah
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import org.json.JSONObject

class PrintFragment : BaseFragment() {

    private lateinit var viewModel: PrintViewModel
    private var _binding: FragmentPrintBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PrintAdapter
    private var printSave = PrintSave()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(PrintViewModel::class.java)
        _binding = FragmentPrintBinding.inflate(inflater, container, false)

        binding.mainViewModel = mainViewModel

        adapter = PrintAdapter(PrintListener {

            val bundle = Bundle().apply {
                putSerializable("basket_bundle", it)
            }

            findNavController().navigate(R.id.action_printFragmnt_to_printDetailFragment, bundle)
        })
        binding.recyclerView.adapter = adapter

        mainViewModel.printSelected.observe(viewLifecycleOwner, {
            printSave.baskets = it

            adapter.submitList( it )
        })

        mainViewModel.grandTotal = 0
        mainViewModel.grandTotalText.value = mainViewModel.grandTotal.toString().convertRupiah()

        if( mainViewModel.printSelected.value != null){
            for (basket in mainViewModel.printSelected.value!!){
                mainViewModel.grandTotal += basket.printArray.sub_total
            }
            mainViewModel.grandTotalText.value = mainViewModel.grandTotal.toString().convertRupiah()
        }

        binding.rgPengiriman.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbPickup -> {
                    viewModel.methodDelivery = MethodDelivery("DLV001","Pickup", "0")
                }
                R.id.rbJNE -> {
                    viewModel.methodDelivery = MethodDelivery("DLV002","JNE", "0")

                    getOngkir()
                }
            }
        }

        binding.btnNext.setOnClickListener {
//            Logger.d(
//                viewModel.printSave(
//                    mainViewModel.printSelected.value!!,
//                    mainViewModel.grandTotal.toString(),
//                    mainViewModel.companySelected.comp_id,
//                    mainViewModel.companySelected.provinces_id,
//                    mainViewModel.companySelected.regencies_id,
//                    "0",
//                    "Pickup"
//                )
//            )
            printSave()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun printSave(){

        viewModelAPI.printSave2(pref.apiToken, pref.user?.id.toString(), JSONObject(),
            viewModel.printSave(
                pref.user?.id.toString(),
                mainViewModel.printSelected.value!!,
                mainViewModel.grandTotal.toString(),
                mainViewModel.companySelected.comp_id,
                mainViewModel.companySelected.provinces_id,
                mainViewModel.companySelected.regencies_id,
                viewModel.methodDelivery.methodPrice,
                viewModel.methodDelivery.methodName,
                viewModel.methodDelivery.methodCode
            )
        ).observe(viewLifecycleOwner, {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        mainViewModel.showProgress(true)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.showProgress(false)

                        it.data?.let { api ->

                            Logger.d( Gson().toJson(api) )

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

    private fun getOngkir(){
        viewModelAPI.ongkir(
            viewModel.printSave(
                pref.user?.id.toString(),
                mainViewModel.printSelected.value!!,
                mainViewModel.grandTotal.toString(),
                mainViewModel.companySelected.comp_id,
                mainViewModel.companySelected.provinces_id,
                mainViewModel.companySelected.regencies_id,
                viewModel.methodDelivery.methodPrice,
                viewModel.methodDelivery.methodName,
                viewModel.methodDelivery.methodCode
            )
        ).observe(viewLifecycleOwner, {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        mainViewModel.showProgress(true)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.showProgress(false)

                        it.data?.let { api ->

                            Logger.d( Gson().toJson(api) )

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