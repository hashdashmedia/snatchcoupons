package com.app.couponapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.app.couponapp.BuildConfig
import com.app.couponapp.R
import com.app.couponapp.data.model.DrawerResponse
import com.app.couponapp.databinding.ActivityMainBinding
import com.app.couponapp.databinding.SortItemLayoutBinding
import com.app.couponapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private var drawerResponse: DrawerResponse?=null
    private val couponViewModel by viewModels<CouponViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hideStatusActionBar()
        setObserver()
        setUpDrawer()
        setDrawerListener()
        setClickListeners()
    }

    private fun setObserver() {
        couponViewModel.collectDrawerData().launchAndCollectIn(this, Lifecycle.State.STARTED){ it->
            when(it){
                is Resource.Success -> {
                   drawerResponse=it.data
                }
                is Resource.Loading -> {}
                is Resource.Error ->  {}
                else -> {}
            }
        }
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
        binding.rootDrawerLayout.llAboutUs.setOnClickListener {
           navigateToCustomWebView(drawerResponse?.aboutUrl?:"")
        }
        binding.rootDrawerLayout.llPrivacyPolicy.setOnClickListener {
            navigateToCustomWebView(drawerResponse?.privacyUrl?:"")
        }
        binding.rootDrawerLayout.llTermCondition.setOnClickListener {
            navigateToCustomWebView(drawerResponse?.tncUrl?:"")
        }
        binding.rootDrawerLayout.llHome.setOnClickListener {
            binding.bottomNav.selectedItemId=R.id.navHomePage
            binding.drawerLayoutRoot.close()
        }
        binding.rootDrawerLayout.llShare.setOnClickListener {
            shareApp("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}\n\n")
        }
        binding.rootDrawerLayout.llRateApp.setOnClickListener {
            openWebView("market://details?id=${BuildConfig.APPLICATION_ID}")
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
        binding.bottomNav.setOnItemReselectedListener{}
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
              R.id.navDeal->{
                  navController.navigate(R.id.navDeal)
                  true
              }R.id.navCoupon->{
                  navController.navigate(R.id.navCoupon)
                  true
              }R.id.navHomePage->{
                  navController.navigate(R.id.navHomePage)
                  true
              }
              else -> {

                  return@setOnItemSelectedListener true
              }

          }
      }
    }

    private fun navigateToCustomWebView(url: String) {
        navController.navigate(R.id.nav_webView, bundleOf("url" to url))
        binding.drawerLayoutRoot.close()
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