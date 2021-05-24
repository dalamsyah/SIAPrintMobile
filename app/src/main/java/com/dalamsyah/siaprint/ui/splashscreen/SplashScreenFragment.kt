package com.dalamsyah.siaprint.ui.splashscreen

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.models.Users
import com.dalamsyah.siaprint.storage.Prefs
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashScreenFragment : BaseFragment(), CoroutineScope {

    var user: Users? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("DEBUGGG", "onCreateView splashscreen")

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        user = Prefs(requireContext()).user

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("DEBUGGG", "onViewCreated splashscreen")

        launch {
            delay(2000)

            if (user != null){
                (activity as AppCompatActivity?)!!.supportActionBar!!.show()
                findNavController().navigate(
                    R.id.action_splashScreenFragment_to_homeFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.splashScreenFragment,
                            true).build()
                )
            } else {
                findNavController().navigate(
                    R.id.action_splashScreenFragment_to_indexFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.splashScreenFragment,
                            true).build()
                )
            }

        }

    }

    override fun onResume() {
        super.onResume()

        Log.d("DEBUGGG", "onResume splashscreen")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

}