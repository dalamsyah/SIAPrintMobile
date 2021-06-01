package com.dalamsyah.siaprint.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.BuildConfig
import com.dalamsyah.siaprint.MainActivity
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentRegisterBinding
import com.dalamsyah.siaprint.models.ResponseAPI
import com.dalamsyah.siaprint.retrofit.Status
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.google.gson.Gson

class RegisterFragment : BaseFragment(), MainActivity.DialogListener {

    private lateinit var viewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).setupClickDialog(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        (activity as MainActivity).setupClickDialog(this)

        binding.btnRegister.setOnClickListener {
            doRegister()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun doRegister(){
        viewModelAPI.register(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString(),
            binding.etUlangiPassword.text.toString(),
            binding.etUsername.text.toString()).observe(viewLifecycleOwner, {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        mainViewModel.showProgress(true)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.showProgress(false)

                        it.data?.let { api ->

                            val json = Gson().toJson(api, ResponseAPI::class.java)
                            val data = Gson().fromJson(json, ResponseAPI::class.java)

                            if (api.result == null || (api.result as List<*>).isEmpty()) {

                                if (api.message is String) {
                                    if (data.status == 1){
                                        mainViewModel.showDialog( api.message as String, false )
                                    } else {
                                        mainViewModel.showDialog( api.message as String,
                                            konf = false,
                                            isAction = true
                                        )
                                    }

                                } else if (api.message is String){
                                    val msg = api.message as Map<String, String>

                                    var message = ""
                                    for((_, v) in msg){
                                        message += "$v \n"
                                    }
                                    mainViewModel.showDialog( message, false )
                                }

                            } else {
                                mainViewModel.showDialog(resources.getString(R.string.error_api_parsing), false )
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

    override fun onOK() {
        if (mainViewModel.isActionButtonDialogOK){
            findNavController().popBackStack()
        }
    }

}