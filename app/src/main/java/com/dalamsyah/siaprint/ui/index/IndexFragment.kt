package com.dalamsyah.siaprint.ui.index

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.MainActivity
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.`interface`.DrawerLockInterface
import com.dalamsyah.siaprint.databinding.FragmentIndexBinding
import com.dalamsyah.siaprint.databinding.FragmentLoginBinding
import com.dalamsyah.siaprint.ui.login.LoginViewModel
import com.dalamsyah.siaprint.ui.utils.BaseFragment

class IndexFragment : BaseFragment() {

    private lateinit var indexViewModel: IndexViewModel
    private var _binding: FragmentIndexBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        indexViewModel = ViewModelProvider(this).get(IndexViewModel::class.java)
        _binding = FragmentIndexBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
        }

        binding.btnLogin.setOnClickListener {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            findNavController().navigate(R.id.action_indexFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            findNavController().navigate(R.id.action_indexFragment_to_registerFragment)
        }

        (activity as DrawerLockInterface).setDrawerLock(true)

        return binding.root
    }

}