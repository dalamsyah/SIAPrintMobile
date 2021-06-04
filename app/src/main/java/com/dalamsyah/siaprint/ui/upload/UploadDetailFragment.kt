package com.dalamsyah.siaprint.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dalamsyah.siaprint.databinding.FragmentUploadDetailBinding
import com.dalamsyah.siaprint.ui.utils.BaseFragment

class UploadDetailFragment : BaseFragment() {

    private lateinit var viewModel: UploadViewModel
    private var _binding: FragmentUploadDetailBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        _binding = FragmentUploadDetailBinding.inflate(inflater, container, false)



        // Inflate the layout for this fragment
        return binding.root
    }

}