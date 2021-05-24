package com.dalamsyah.siaprint.ui.barang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentBarangBinding

class BarangFragment : Fragment() {

    private lateinit var barangViewModel: BarangViewModel
    private var _binding: FragmentBarangBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        barangViewModel = ViewModelProvider(this).get(BarangViewModel::class.java)
        _binding = FragmentBarangBinding.inflate(inflater, container, false)

        val textView = binding.textBarang
        barangViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        textView.setOnClickListener {
            findNavController().navigate(R.id.action_nav_barang_to_slideshowFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}