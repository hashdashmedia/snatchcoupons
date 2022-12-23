package com.app.couponapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.app.couponapp.BuildConfig
import com.app.couponapp.R
import com.app.couponapp.databinding.ActivityMainBinding
import com.app.couponapp.databinding.SortItemLayoutBinding
import com.app.couponapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hideStatusActionBar()
        setUpDrawer()
        setDrawerListener()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.contentMainLayout.appBarLayout.ivToolbarShare.setOnClickListener {
          shareApp("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}\n\n")
        }
    }

    private fun setUpDrawer() {
        setSupportActionBar(binding.contentMainLayout.appBarLayout.toolbar)
        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        // mAppBarConfiguration = AppBarConfiguration.Builder(navController.graph)
        mAppBarConfiguration = AppBarConfiguration(
            setOf(R.id.navHomePage, R.id.navCoupon, R.id.navDeal),
            binding.drawerLayoutRoot
        )
        /*.setOpenableLayout(binding.drawerLayout)
           .build()*/
        setupActionBarWithNavController(this, navController, mAppBarConfiguration)
        setupWithNavController(binding.navigationView, navController)
        setupWithNavController(binding.bottomNav, navController)
        binding.navigationView.bringToFront()
    }

    private fun setDrawerListener() {

        binding.rootDrawerLayout.llHome.setOnClickListener {
            binding.bottomNav.selectedItemId=R.id.navHomePage
            binding.drawerLayoutRoot.close()
        }
        binding.rootDrawerLayout.llShare.setOnClickListener {
            shareApp("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}\n\n")
        }
        binding.rootDrawerLayout.llRateApp.setOnClickListener {
            openPlayStore("market://details?id=${BuildConfig.APPLICATION_ID}")
        }
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navHomePage -> {
                    binding.contentMainLayout.appBarLayout.ivToolbarShare.makeGone()
                }
                else -> {
                    binding.contentMainLayout.appBarLayout.ivToolbarShare.makeVisible()
                }
            }
            supportActionBar?.title = ""
            binding.contentMainLayout.appBarLayout.tvToolbar.text = destination.label.toString()
        }
      binding.bottomNav.setOnItemSelectedListener {
          when(it.itemId){
              R.id.nav_graph_sort->{
                  customAlertDialog(R.style.CustomAlertDialog
                      ,SortItemLayoutBinding.inflate(layoutInflater)){binding,dialog->
                      binding.tvCancel.setOnClickListener {
                          dialog.dismiss()
                      }
                      binding.tvOk.setOnClickListener {
                          dialog.dismiss()
                          when(binding.rgFilter.checkedRadioButtonId){
                             R.id.rbDateFilter->{showMessage("rbDate")}
                             R.id.rbAmountFilter->{showMessage("rbAmount")}
                          }
                      }
                      dialog.show()
                  }
                  true
              }
              else -> {true}

          }
      }
    }


    override fun onSupportNavigateUp(): Boolean {
        return (navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp())
    }

    fun getCurrentTab(): CharSequence =
        binding.bottomNav.menu.findItem(binding.bottomNav.selectedItemId).title

    fun hideShowBottomNav(status: String) {
        when (status) {
            "show" -> binding.bottomNav.makeVisible()
            "hide" -> binding.bottomNav.makeGone()
        }
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()
    }

}