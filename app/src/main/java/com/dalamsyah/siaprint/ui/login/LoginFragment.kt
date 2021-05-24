package com.dalamsyah.siaprint.ui.login

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.BuildConfig
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentLoginBinding
import com.dalamsyah.siaprint.models.ResponseAPI
import com.dalamsyah.siaprint.models.ResultErrorLogin
import com.dalamsyah.siaprint.models.Users
import com.dalamsyah.siaprint.retrofit.APIViewModel
import com.dalamsyah.siaprint.retrofit.Resource
import com.dalamsyah.siaprint.retrofit.Status
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.dalamsyah.siaprint.utils.StringUtil
import com.dalamsyah.siaprint.utils.toObject
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*
import kotlin.String as String

class LoginFragment : BaseFragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.blue_700_siaprint)
//            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        binding.tvLupaPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    /*
    * login
    * */
    private fun doLogin(){
        viewModelAPI.login(binding.etUsername.text.toString(), binding.etPassword.text.toString()).observe(viewLifecycleOwner, {
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

                            if (api.result == null) {

                                if (api.message is String) {
                                    mainViewModel.showDialog( api.message as String, false )
                                } else if (api.message is Map<*, *>){
                                    val msg = api.message as Map<String, String>

                                    var message = ""
                                    for((k, v) in msg){
                                        message += "$v \n"
                                    }

                                    mainViewModel.showDialog( message.replace("Login", "Username or Email", ignoreCase = true), false )

                                }

                            } else {

                                if (api.result is Map<*, *>) {

                                    val userMap = api.result as Map<String, Users>

                                    val userConvert = JSONObject(userMap).get("user").toString()
                                    val user = Gson().fromJson(userConvert, Users::class.java)

                                    if (user is Users) {
                                        pref.user = user

                                        findNavController().navigate(
                                            R.id.action_loginFragment_to_homeFragment,
                                            null,
                                            NavOptions.Builder()
                                                .setPopUpTo(R.id.indexFragment,
                                                    true).build())

                                    } else {
                                        mainViewModel.showDialog(resources.getString(R.string.error_api_parsing), false )
                                    }

                                } else {
                                    mainViewModel.showDialog(resources.getString(R.string.error_api_parsing), false )
                                }
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