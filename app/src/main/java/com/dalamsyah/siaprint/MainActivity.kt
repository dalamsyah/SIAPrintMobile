package com.dalamsyah.siaprint

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dalamsyah.siaprint.`interface`.DrawerLockInterface
import com.dalamsyah.siaprint.`interface`.ProgressInterface
import com.dalamsyah.siaprint.databinding.ActivityMainBinding
import com.dalamsyah.siaprint.models.Users
import com.dalamsyah.siaprint.storage.Prefs

class MainActivity : AppCompatActivity(),
    DrawerLockInterface,
    ProgressInterface,
    View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    interface DialogListener {
        fun onOK()
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels()
    private var mListener: DialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf( R.id.nav_home, R.id.uploadFragment, R.id.nav_logout, R.id.nav_barang), drawerLayout)

//        val topLevelDestinations = setOf(R.id.nav_home)
//        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
//            .setOpenableLayout(drawerLayout)
//            .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

        binding

//        val contentMain = ContentMainBinding.inflate(layoutInflater)
        val progressBarLayout = findViewById<ConstraintLayout>(R.id.progressBarLayout)
        val dialogLayout = findViewById<ConstraintLayout>(R.id.dialogLayout)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val btnOK = findViewById<Button>(R.id.btnOK)
        val tvMsg = findViewById<TextView>(R.id.tvMsg)

        val nav = navView.getHeaderView(0)

        val tvEmail = nav.findViewById<TextView>(R.id.tvEmail)
        val tvName = nav.findViewById<TextView>(R.id.tvName)

        val user = Prefs(this).user
        Log.d("DEBUGGG", user.toString())
        user?.apply {
            tvName.text = username.toString()
            tvEmail.text = email.toString()
        }

        viewModel.progressBarLayout.observe(this, {
            if(it) {
                progressBarLayout.visibility = View.VISIBLE
            }else{
                progressBarLayout.visibility = View.GONE
            }
        })

        viewModel.dialogLayout.observe(this, {
            if(it) {
                dialogLayout.visibility = View.VISIBLE
            }else{
                dialogLayout.visibility = View.GONE
            }
        })

        viewModel.dialogKonfirmasi.observe(this, {
            if(it) {
                btnCancel.visibility = View.VISIBLE
            }else{
                btnCancel.visibility = View.GONE
            }
        })

        tvMsg.movementMethod = LinkMovementMethod.getInstance()
        viewModel.dialogMsg.observe(this, {

            tvMsg.text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)

//            tvMsg.text = it
//            Linkify.addLinks(tvMsg, Linkify.WEB_URLS)
        })

        btnOK.setOnClickListener(this)

//        btnOK.setOnClickListener {
//            viewModel.hideDialog()
//            dialogInterface.onClickOK()
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.nav_home -> {
                navController.navigate(R.id.nav_home)
            }
            R.id.uploadFragment -> navController.navigate(R.id.uploadFragment)
            R.id.nav_logout -> {
                doLogout()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun doLogout(){
        val pref = Prefs(this)
        pref.user = null
        viewModel.setUser( Users() )

        navController.navigate(
            R.id.indexFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.nav_home,
                    true).build())
    }

    override fun setDrawerLock(lock: Boolean) {
        if (lock) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun setShowProgress(show: Boolean) {
//        val progressLayout = findViewById<ConstraintLayout>(R.id.progressBarLayout)
//        if(show) {
//            progressLayout.visibility = View.VISIBLE
//        }else{
//            progressLayout.visibility = View.GONE
//        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnOK -> {
                viewModel.hideDialog()
                if (mListener != null){
                    mListener!!.onOK()
                }
            }
        }
    }

    fun setupClickDialog(listener: DialogListener?){
        this.mListener = listener
    }

}