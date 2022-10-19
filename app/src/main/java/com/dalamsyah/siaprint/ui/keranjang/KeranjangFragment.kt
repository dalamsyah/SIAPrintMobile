package com.dalamsyah.siaprint.ui.keranjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dalamsyah.siaprint.BuildConfig
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentKeranjangBinding
import com.dalamsyah.siaprint.models.Company
import com.dalamsyah.siaprint.models.PrintSave
import com.dalamsyah.siaprint.retrofit.Status
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.orhanobut.logger.Logger

class KeranjangFragment : BaseFragment() {

    private lateinit var viewModel: KeranjangViewModel
    private var _binding: FragmentKeranjangBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: KeranjangAdapter
    private var listDialog = mutableListOf<Company>()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)

        adapter = KeranjangAdapter(KeranjangListener { model ->
            model.selected = !model.selected
            viewModel.checkSelected( adapter.currentList )
            adapter.notifyDataSetChanged()
        })
        binding.recyclerView.adapter = adapter

        viewModel.listBasket.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.isEnableBtnNext.observe(viewLifecycleOwner, {
            binding.btnNext.isEnabled = it
        })

        viewModel.listCompanySelected.observe(viewLifecycleOwner, {
            listDialog.addAll(it)
            for (model in it){
                var text = "<b>${model.comp_name}</b><br>${model.comp_address}, ${model.regencies_name}, ${model.provinces_name} "
                binding.tvAlamat.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)

                /**
                 * set company selected
                 */
                mainViewModel.companySelected = model
            }
        })

        binding.tvPilihAlamat.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            val rowList: View = layoutInflater.inflate(R.layout.layout_recyclerview, null)
            val listView = rowList.findViewById<RecyclerView>(R.id.recyclerView)
            val adapterDialog = CompanyAdapter(listDialog, requireContext() )
            listView.adapter = adapterDialog
            adapterDialog.notifyDataSetChanged()
            builder.setView(listView)

            builder.show()
        }

        binding.btnNext.setOnClickListener {

            var printSave = PrintSave()
            printSave.csrf_test_name = ""
            printSave.fn_addr_default_regencies_id = mainViewModel.companySelected.regencies_id
            printSave.fn_addr_default_provinces_id = mainViewModel.companySelected.provinces_id
            printSave.fn_addr_default_address = mainViewModel.companySelected.comp_address
            printSave.fn_addr_default_regencies_name = mainViewModel.companySelected.regencies_name
            printSave.fn_addr_default_provinces_name = mainViewModel.companySelected.provinces_name
            printSave.fn_addr_default_postcode = ""
            printSave.fn_addr_default_phone = pref.user!!.phone!!
            printSave.fn_addr_default_receiver = ""
            printSave.fn_total_amount2 = "0"
            printSave.fn_total_amount = "0"
            printSave.fn_total_weigth = "0"
            printSave.total_row = ""
            printSave.company_id = mainViewModel.companySelected.comp_id
            printSave.company_provinces_id = mainViewModel.companySelected.provinces_id
            printSave.company_regencies_id = mainViewModel.companySelected.regencies_id
            printSave.fn_total_delv = ""
            printSave.fn_delv_info = ""
            printSave.fn_delv = ""
            printSave.delv = ""
            printSave.arr_detail = mutableListOf()

            mainViewModel.setPrintSave( printSave )

            mainViewModel.setPrintSelected( viewModel.getListBasketSelected() )
            findNavController().navigate(R.id.action_keranjangFragment_to_printFragmnt)
        }

        getKeranjang()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getKeranjang(){
        viewModelAPI.keranjang(pref.apiToken, pref.user?.id.toString()).observe(viewLifecycleOwner, {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        mainViewModel.showProgress(true)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.showProgress(false)

                        it.data?.let { api ->

                            Logger.d( api.result )

                            if (api.result != null) {

                                viewModel.setListBasket( api.result!!.basket )
                                viewModel.setListCompanySelected( api.result!!.company_selected )

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