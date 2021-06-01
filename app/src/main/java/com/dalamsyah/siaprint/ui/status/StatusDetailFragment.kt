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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatusDetailBinding.inflate(inflater, container, false)

        adapter = StatusDetailAdapter( StatusDetailListener { model ->
        })
        binding.recyclerView.adapter = adapter

        var transH = arguments?.getSerializable("trans_h") as TransactionPrintH
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
        } else {
            binding.btnBayar.visibility = View.GONE
            binding.btnCancel.visibility = View.GONE
        }

        (activity as MainActivity).setupClickDialog(this)

        binding.btnBayar.setOnClickListener {
            mainViewModel.showDialog("Anda yakin melakukan pembayaran?",
                konf = true,
                isAction = true
            )
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onOK() {
        Toast.makeText(context, "Oke clicked", Toast.LENGTH_SHORT).show()

        viewModelAPI.payment(apitoken = pref.apiToken, payment_no = "91234", total_amount = "1", payment_type = "PYM001",
        payment_name = "OVO", phone_no = "08979598671", vendor_code = "VDR001").observe(viewLifecycleOwner, {
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

    override fun onCancel() {

    }

}