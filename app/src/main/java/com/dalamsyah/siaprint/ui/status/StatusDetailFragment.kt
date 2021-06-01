package com.dalamsyah.siaprint.ui.status

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.MainActivity
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentStatusBinding
import com.dalamsyah.siaprint.databinding.FragmentStatusDetailBinding
import com.dalamsyah.siaprint.models.Siaprint
import com.dalamsyah.siaprint.models.TransactionPrintD
import com.dalamsyah.siaprint.models.TransactionPrintH
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.orhanobut.logger.Logger

class StatusDetailFragment : BaseFragment() {

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

        // Inflate the layout for this fragment
        return binding.root
    }

}