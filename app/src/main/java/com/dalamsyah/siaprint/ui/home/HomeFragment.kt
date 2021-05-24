package com.dalamsyah.siaprint.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.`interface`.DrawerLockInterface
import com.dalamsyah.siaprint.databinding.FragmentHomeBinding
import com.dalamsyah.siaprint.ui.utils.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.cvUpload.setOnClickListener {
//            findNavController().navigate(R.id.action_fragment_home_to_uploadFragment)
//        }

        Toast.makeText(context, pref.user?.username+"", Toast.LENGTH_SHORT).show()

        (activity as DrawerLockInterface).setDrawerLock(false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}