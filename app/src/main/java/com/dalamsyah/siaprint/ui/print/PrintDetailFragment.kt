package com.dalamsyah.siaprint.ui.print

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.BuildConfig
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentKeranjangBinding
import com.dalamsyah.siaprint.databinding.FragmentPrintDetailBinding
import com.dalamsyah.siaprint.models.*
import com.dalamsyah.siaprint.retrofit.Status
import com.dalamsyah.siaprint.ui.keranjang.KeranjangViewModel
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.dalamsyah.siaprint.utils.convertRupiah
import com.orhanobut.logger.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrintDetailFragment : BaseFragment() {

    private lateinit var viewModel: PrintViewModel
    private var _binding: FragmentPrintDetailBinding? = null
    private val binding get() = _binding!!

    private var basket: Basket = Basket()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(PrintViewModel::class.java)
        _binding = FragmentPrintDetailBinding.inflate(inflater, container, false)

        basket = arguments?.getSerializable("basket_bundle") as Basket

        binding.viewModel = viewModel
        viewModel.setCopy("1")

        val adapterUkuranKertas = object : ArrayAdapter<SizeData>(requireContext(), R.layout.support_simple_spinner_dropdown_item, viewModel.listUkuranKertasSelected) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val text1 = view.findViewById(android.R.id.text1) as TextView
//                val text2 = view.findViewById<TextView>(R.id.tvText2)
                text1.text = viewModel.listUkuranKertasSelected[position].size_name +" - "+ viewModel.listUkuranKertasSelected[position].size_detail
//                text2.text = listUkuranKertas[position].size_detail
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getView(position, convertView, parent)
                val text1 = view.findViewById(android.R.id.text1) as TextView
//                val text2 = view.findViewById<TextView>(R.id.tvText2)
                text1.text = viewModel.listUkuranKertasSelected[position].size_name +" - "+ viewModel.listUkuranKertasSelected[position].size_detail
//                text2.text = listUkuranKertas[position].size_detail
                return view
            }

        }
        binding.spUkuranKertas.adapter = adapterUkuranKertas

        val adapterJenisKertas = object : ArrayAdapter<PriceData>(requireContext(), R.layout.support_simple_spinner_dropdown_item, viewModel.listJenisKertasSelected) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val text1 = view.findViewById(android.R.id.text1) as TextView
                text1.text = viewModel.listJenisKertasSelected[position].type_paper_name +" - "+ viewModel.listJenisKertasSelected[position].price.convertRupiah()
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getView(position, convertView, parent)
                val text1 = view.findViewById(android.R.id.text1) as TextView
                text1.text = viewModel.listJenisKertasSelected[position].type_paper_name +" - "+ viewModel.listJenisKertasSelected[position].price.convertRupiah()
                return view
            }
        }
        binding.spJenisKertas.adapter = adapterJenisKertas

        val adapterFinishing = object : ArrayAdapter<PriceFinishData>(requireContext(), R.layout.support_simple_spinner_dropdown_item, viewModel.listFinishingSelected) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val text1 = view.findViewById(android.R.id.text1) as TextView
                text1.text = viewModel.listFinishingSelected[position].finish_text +" - "+ viewModel.listFinishingSelected[position].price.convertRupiah()
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getView(position, convertView, parent)
                val text1 = view.findViewById(android.R.id.text1) as TextView
                text1.text = viewModel.listFinishingSelected[position].finish_text +" - "+ viewModel.listFinishingSelected[position].price.convertRupiah()
                return view
            }
        }
        binding.spFinishing.adapter = adapterFinishing

        /**
         * set spinner jenis kertas
         */
        binding.spUkuranKertas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                viewModel.price_code = viewModel.listUkuranKertasSelected[position].size_code

                viewModel.listJenisKertas.clear()
                viewModel.listJenisKertas.addAll(viewModel.listPrintDetail.value?.price_data!!)

                viewModel.filterJenisKertas( viewModel.ink_code, viewModel.price_code )
                adapterJenisKertas.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        /**
         * set spinner finishing
         */
        binding.spJenisKertas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                viewModel.listFinishing.clear()
                viewModel.listFinishing.addAll( viewModel.listPrintDetail.value?.pricefinish_data!! )

                viewModel.filterListFinishing( viewModel.price_code, viewModel.listJenisKertas[position].price_code )

                viewModel.jenisKertasCurrent = viewModel.listJenisKertasSelected[position]

                adapterFinishing.notifyDataSetChanged()

                viewModel.generateGrandTotal()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        /**
         * set grand total on spinner finishing
         */
        binding.spFinishing.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.finishingPrice = viewModel.listFinishingSelected[position]

                viewModel.generateGrandTotal()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.rgPrint.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId){
                R.id.rbLaser -> {
                    viewModel.ink_code = "INK001"
                    viewModel.filterUkuranKertas( viewModel.ink_code )
                    adapterUkuranKertas.notifyDataSetChanged()
                }
                R.id.rbTinta -> {
                    viewModel.ink_code = "INK002"
                    viewModel.filterUkuranKertas( viewModel.ink_code )
                    adapterUkuranKertas.notifyDataSetChanged()
                }
            }

        }

        viewModel.listPrintDetail.observe(viewLifecycleOwner, {
            viewModel.listUkuranKertas.addAll( it.size_data!! )
        })

        viewModel.grangTotal.observe(viewLifecycleOwner, {
            binding.tvGrandTotal.text = "Total : "+ it.toString().convertRupiah()
        })

        viewModel.copy.observe(viewLifecycleOwner, {
            viewModel.generateGrandTotal()
        })

        binding.btnNext.setOnClickListener {
            isValid()
//            viewModel.printSave()
//            printSave()
        }

        binding.btnBatal.setOnClickListener {
            findNavController().popBackStack()
        }

        getPrintDetail()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun isValid(){
        if (binding.rgPrint.checkedRadioButtonId == -1){
            mainViewModel.showDialog(resources.getString(R.string.warning_tipe_print), false )
            return
        }

        val printSave = mainViewModel.printSave.value
        val printDetail = mutableListOf<PrintArray>()

        val printArray = PrintArray()
        printArray.check_ink = viewModel.ink_code
        printArray.id_papper = viewModel.jenisKertasCurrent.size_code
        printArray.type_paper = viewModel.jenisKertasCurrent.type_paper_code
        printArray.pages = basket.pages_tot
        printArray.count_print = viewModel.copy.value!!
        printArray.finishing = viewModel.finishingPrice.finish_code
        printArray.remarks = binding.etPesan.text.toString()
        printArray.fn_filename = basket.filename
        printArray.fn_filename_random = basket.filename_random
        printArray.fn_size_code = printArray.id_papper
        printArray.fn_ink_code = printArray.check_ink
        printArray.fn_type_paper_code = printArray.type_paper
        printArray.fn_copy = printArray.count_print
        printArray.fn_finish_code = printArray.finishing
        printArray.fn_pages = printArray.pages
        printArray.fn_amount = viewModel.grangTotal.value.toString()
        printArray.fn_price = viewModel.jenisKertasCurrent.price
        printArray.fn_price_finish = viewModel.finishingPrice.price
        printArray.fn_weigth = "0.000"
        printArray.fn_weigth_finish = "0"
        printArray.fn_weigth_item =
        printArray.sub_total = viewModel.grangTotal.value!!

        printDetail.add(printArray)

        printSave!!.arr_detail = printDetail

        basket.isComplete = true
        basket.printArray = printArray
        mainViewModel.replacePrintSelected( basket )

//        mainViewModel.grandTotal += viewModel.grangTotal.value!!
//        mainViewModel.grandTotalText.value = mainViewModel.grandTotal.toString().convertRupiah()

        findNavController().popBackStack()

        return
    }

    private fun getPrintDetail(){
        viewModelAPI.printDetail(pref.apiToken, mainViewModel.companySelected.comp_id, pref.user?.id.toString()).observe(viewLifecycleOwner, {
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

                                viewModel.setPrintDetail( api.result!!.print )

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