package com.app.couponapp.ui

import android.os.Bundle
import android.util.Log
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
import com.facebook.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    private val addView by lazy {
        AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
    }
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
        adsFbSetUp()
        loadInterAdMob()
    }

    private fun adsFbSetUp() {
        /**fb banner ads**/
        binding.bannerAdFb.addView(addView)
        AdSettings.addTestDevice("52db118f-52d3-4ec3-899b-ebdebeb6b02e")
        /*addView.loadAd(addView.buildLoadAdConfig().withAdListener(BannerFbAdsImp(object :CustomAdsListener{
            override fun onAdLoad(p0: Ad?){}
        })).build())*/
        addView.loadAd(addView.buildLoadAdConfig().withAdListener(object:AdListener{
            override fun onError(p0: Ad?, p1: AdError?) {
                showMessage("${p1?.errorMessage} ${p1?.errorCode}")
            }

            override fun onAdLoaded(p0: Ad?) {
                showMessage("loadFbAdd}")
            }

            override fun onAdClicked(p0: Ad?) {
                showMessage("FbAddClick")
            }

            override fun onLoggingImpression(p0: Ad?) {
                showMessage("fb add onLoggingImpression")
            }

        }).build())
    }

    fun loadInterAdMob(){
        /**google interstitialAd ads**/
        val adRequest: AdRequest = AdManagerAdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712",adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.e(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.e(TAG, it) }
                    mInterstitialAd = null
                }
            })
    }
    fun showInterAdMob(){
        mInterstitialAd?.show(this)
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

    private fun setDrawerListener(){
        binding.rootDrawerLayout.llAboutUs.setOnClickListener {
           navigateToCustomWebView(drawerResponse?.data?.aboutUrl?:"")
        }
        binding.rootDrawerLayout.llPrivacyPolicy.setOnClickListener {
            navigateToCustomWebView(drawerResponse?.data?.privacy_policy?:"")
        }
        binding.rootDrawerLayout.llTermCondition.setOnClickListener {
            navigateToCustomWebView(drawerResponse?.data?.tncUrl?:"")
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

        binding.bottomNav.setOnItemReselectedListener{
            when(it.itemId) {
                R.id.nav_graph_sort -> {
                    openSortDialog()
                }
            }
        }

      binding.bottomNav.setOnItemSelectedListener {
          when(it.itemId){
              R.id.nav_graph_sort->{
                  openSortDialog()
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

    private fun openSortDialog() {
        customAlertDialog(R.style.CustomAlertDialog
            ,SortItemLayoutBinding.inflate(layoutInflater)){binding,dialog->
            binding.tvCancel.setOnClickListener {
                dialog.dismiss()
            }
            binding.tvOk.setOnClickListener {
                dialog.dismiss()
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
                if(currentFragment is HomePageFragment) {
                    when (binding.rgFilter.checkedRadioButtonId) {
                        R.id.rbDateFilter -> {
                            currentFragment.filterByDate()
                        }
                        R.id.rbAmountFilter -> {
                            currentFragment.filterByValue()
                        }
                    }
                }
            }
            dialog.show()
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

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        addView.destroy()
    }
}