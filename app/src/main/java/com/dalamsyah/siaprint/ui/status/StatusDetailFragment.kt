package com.dalamsyah.siaprint.ui.status

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.BuildConfig
import com.dalamsyah.siaprint.MainActivity
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentStatusBinding
import com.dalamsyah.siaprint.databinding.FragmentStatusDetailBinding
import com.dalamsyah.siaprint.models.Siaprint
import com.dalamsyah.siaprint.models.TransactionPrintD
import com.dalamsyah.siaprint.models.TransactionPrintH
import com.dalamsyah.siaprint.retrofit.Status
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.orhanobut.logger.Logger

class StatusDetailFragment : BaseFragment(), MainActivity.DialogListener {

    private val viewModel: StatusViewModel by activityViewModels()
    private var _binding: FragmentStatusDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StatusDetailAdapter

    private var transH: TransactionPrintH = TransactionPrintH()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).setupClickDialog(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatusDetailBinding.inflate(inflater, container, false)

        adapter = StatusDetailAdapter( StatusDetailListener { model ->
        })
        binding.recyclerView.adapter = adapter

        transH = arguments?.getSerializable("trans_h") as TransactionPrintH
        (activity as MainActivity).updateTitleToolabar("Detail #${transH.print_h_code}")

        viewModel.listTransD.observe(viewLifecycleOwner, {
            viewModel.filterById( transH, it)
        })

        viewModel.listTransDSelected.observe(viewLifecycleOwner, {
            Logger.d(it)
            adapter.submitList(it)
        })

        if (transH.trsc_h_status.equals(Siaprint.WAITING_PAYMENT)){
            binding.btnBayar.visibility = View.VISIBLE
            binding.btnCancel.visibility = View.VISIBLE
            binding.tvLabelPembayaran.visibility = View.VISIBLE
            binding.etNoOVO.visibility = View.VISIBLE
        } else {
            binding.btnBayar.visibility = View.GONE
            binding.btnCancel.visibility = View.GONE
            binding.tvLabelPembayaran.visibility = View.GONE
            binding.etNoOVO.visibility = View.GONE
        }

        binding.btnBayar.setOnClickListener {

            if (binding.etNoOVO.text.toString().trim() == ""){
                mainViewModel.showDialog(resources.getString(R.string.no_ovo_masih_kosong), false )
            } else {
                (activity as MainActivity).setupClickDialog(this)

                mainViewModel.showDialog("Anda yakin melakukan pembayaran?",
                    konf = true,
                    isAction = true
                )
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onOK() {

        viewModelAPI.payment(apitoken = pref.apiToken, payment_no = transH.print_h_code.toString(), total_amount = transH.amount_h.toString(), payment_type = Siaprint.PAYMENT_TYPE_CODE_OVO,
        payment_name = Siaprint.PAYMENT_TYPE_OVO, phone_no = binding.etNoOVO.text.toString(), vendor_code = Siaprint.VENDOR1).observe(viewLifecycleOwner, {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        mainViewModel.showProgress(true)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.showProgress(false)

                        it.data?.let { api ->

                            Logger.d( api.result )

                            if (api.result!!.payment != null) {

                                findNavController().popBackStack()
                                mainViewModel.showDialog(resources.getString(R.string.no_ovo_masih_kosong), false )

                            } else {

                                mainViewModel.showDialog(resources.getString(R.string.error_api), false )

                            }
                        }
                    }
                    Status.ERROR -> {

                        (activity as MainActivity).setupClickDialog(null)

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

    override fun onCancel() {
        (activity as MainActivity).setupClickDialog(null)
    }

}