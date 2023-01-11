package com.app.couponapp.ui

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
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
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.data.model.DrawerResponse
import com.app.couponapp.databinding.ActivityMainBinding
import com.app.couponapp.databinding.SortItemLayoutBinding
import com.app.couponapp.util.*
import com.bumptech.glide.Glide
import com.facebook.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var mInterstitialGoogleAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    private val addViewFbBanner by lazy {
        AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
    }
    private var interstitiaFblAd:com.facebook.ads.InterstitialAd?=null
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private var drawerResponse: DrawerResponse? = null
    private var listData: List<CouponDataResponseItem>?=null
    private val couponViewModel by viewModels<CouponViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AdSettings.addTestDevice("0bca348d-f3c4-4d13-8af3-352dfca0c8c7")
        //hideStatusActionBar()
        setObserver()
        setUpDrawer()
        setDrawerListener()
        setClickListeners()
    }
    private fun setObserver() {
        couponViewModel.collectDrawerData()
            .launchAndCollectIn(this, Lifecycle.State.STARTED) { it ->
                when (it) {
                    is Resource.Success -> {
                        drawerResponse = it.data
                        Glide.with(this@MainActivity).load(drawerResponse?.data?.storeImageUrl)
                            .into(binding.rootDrawerLayout.ivProfile)
                        setAdsByCheck(
                            drawerResponse?.data?.homeAdBanner,
                            drawerResponse?.data?.homeAdInterstitial
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
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
            navigateToCustomWebView(
                drawerResponse?.data?.aboutUrl ?: "",
                "About Us"
            )
        }
        binding.rootDrawerLayout.llPrivacyPolicy.setOnClickListener {
            navigateToCustomWebView(drawerResponse?.data?.privacy_policy ?: "", "Privacy Policy")
        }
        binding.rootDrawerLayout.llTermCondition.setOnClickListener {
            navigateToCustomWebView(drawerResponse?.data?.tncUrl ?: "", "Term and Condition")
        }
        binding.rootDrawerLayout.llHome.setOnClickListener {
            binding.bottomNav.selectedItemId = R.id.navHomePage
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
                    adsAccCurrentFrag()
                }
                R.id.navCoupon -> {
                    binding.contentMainLayout.appBarLayout.ivToolbarShare.makeVisible()
                    adsAccCurrentFrag()
                }
                R.id.navDeal -> {
                    binding.contentMainLayout.appBarLayout.ivToolbarShare.makeVisible()
                    adsAccCurrentFrag()
                }
                R.id.nav_webView -> {
                    binding.contentMainLayout.appBarLayout.ivToolbarShare.makeVisible()
                    adsAccCurrentFrag()
                }
                else -> {
                    binding.contentMainLayout.appBarLayout.ivToolbarShare.makeVisible()
                }
            }
            supportActionBar?.title = ""
            binding.contentMainLayout.appBarLayout.tvToolbar.text = destination.label.toString()

        }

        binding.bottomNav.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.nav_graph_sort -> {
                    openSortDialog()
                }
            }
        }

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_graph_sort -> {
                    openSortDialog()
                    true
                }
                R.id.navDeal -> {
                    navController.navigate(R.id.navDeal)
                    true
                }
                R.id.navCoupon -> {
                    navController.navigate(R.id.navCoupon)
                    true
                }
                R.id.navHomePage -> {
                    navController.navigate(R.id.navHomePage)
                    true
                }
                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    fun showInterAdd(screen: String? = null) {
        val intAdValue=when(screen){
            "coupon"->drawerResponse?.data?.couponDetailInterstitial
            "deal"->drawerResponse?.data?.dealDetailInterstitial
            else -> { "" }
        }
        if(intAdValue?.equals("google") == true && mInterstitialGoogleAd!=null){
            mInterstitialGoogleAd?.show(this)
            loadGoogleInterAd()
        }
        if(intAdValue?.equals("fb") == true && interstitiaFblAd?.isAdLoaded == true){
            interstitiaFblAd?.show()
            loadFbInterAdd()
        }
    }

    private fun setAdsByCheck(homeAdBanner: String?, homeAdInterstitial: String?) {
        homeAdBanner.takeIf { !it.equals("blank") && (it?.isNotEmpty() == true) }.apply {
            when (this) {
                "google" -> loadBannerGoogleAd()
                "fb" -> adFbBannerSetUp()
                else -> {}
            }
        }?: kotlin.run {
            binding.contentMainLayout.bannerAdFb.makeGone()
            binding.contentMainLayout.adViewBannerGoogle.makeGone()
        }

        drawerResponse?.data?.dealDetailInterstitial.takeIf { !it.equals("blank") && (it?.isNotEmpty() == true) }.run {
            when (this) {
                "google" -> loadGoogleInterAd()
                "fb" -> loadFbInterAdd()
                else -> {}
            }
        }

        drawerResponse?.data?.couponDetailInterstitial.takeIf { !it.equals("blank") && (it?.isNotEmpty() == true) }.run {
            when (this) {
                "google" -> loadGoogleInterAd()
                "fb" -> loadFbInterAdd()
                else -> {}
            }
        }
    }

    fun loadFbInterAdd() {
        if(interstitiaFblAd!=null){
            interstitiaFblAd=null
        }
        interstitiaFblAd = InterstitialAd(this, "YOUR_PLACEMENT_ID")
        if(interstitiaFblAd?.isAdLoaded == false) {
            interstitiaFblAd?.loadAd(
                interstitiaFblAd?.buildLoadAdConfig()
                    ?.withAdListener(InterstitialFbAdsImp(object : CustomAdsListener {
                        override fun onAdLoad(p0: Ad?) {

                        }
                    }))?.build()
            )
        }
    }

    private fun adFbBannerSetUp() {
        /**fb banner ads**/
        binding.contentMainLayout.bannerAdFb.makeVisible()
        binding.contentMainLayout.adViewBannerGoogle.makeGone()
        if(binding.contentMainLayout.bannerAdFb.childCount!=1) {
            binding.contentMainLayout.bannerAdFb.addView(addViewFbBanner)
        }
        addViewFbBanner.loadAd(
            addViewFbBanner.buildLoadAdConfig()
                .withAdListener(BannerFbAdsImp(object : CustomAdsListener {
                    override fun onAdLoad(p0: Ad?) {}
                })).build()
        )
    }

    fun loadBannerGoogleAd() {
        binding.contentMainLayout.bannerAdFb.makeGone()
        binding.contentMainLayout.adViewBannerGoogle.makeVisible()
        val adRequest = AdRequest.Builder().build()
        binding.contentMainLayout.adViewBannerGoogle.loadAd(adRequest)
    }

    fun loadGoogleInterAd() {
        /**google interstitialAd ads**/
        val adRequest: AdRequest = AdManagerAdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.e(TAG, "Ad was loaded.")
                    mInterstitialGoogleAd = interstitialAd
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.e(TAG, it) }
                    mInterstitialGoogleAd = null
                }
            })
    }


    private fun openSortDialog() {
        customAlertDialog(
            R.style.CustomAlertDialog, SortItemLayoutBinding.inflate(layoutInflater)
        ) { binding, dialog ->
            binding.tvCancel.setOnClickListener {
                dialog.dismiss()
            }
            binding.tvOk.setOnClickListener {
                dialog.dismiss()
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
                if (currentFragment is HomePageFragment) {
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

    private fun navigateToCustomWebView(url: String, title: String) {
        navController.navigate(R.id.nav_webView, bundleOf("url" to url, "title" to title))
        binding.contentMainLayout.appBarLayout.tvToolbar.text = title
        binding.drawerLayoutRoot.close()
    }
    fun getCurrentTab(): CharSequence =
        binding.bottomNav.menu.findItem(binding.bottomNav.selectedItemId).title

    fun hideShowBottomNav(status: String) {
        when (status) {
            "show" -> binding.bottomNav.makeVisible()
            "hide" -> binding.bottomNav.makeGone()
        }
    }

    fun couponDetailBannerAd(){
      setAdsByCheck(drawerResponse?.data?.couponDetailBanner,"")
    }

    fun dealDetailBannerAd(){
      setAdsByCheck(drawerResponse?.data?.dealDetailBanner,"")
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp())
    }


    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        addViewFbBanner.destroy()
        interstitiaFblAd?.destroy()
    }

    override fun onBackPressed(){
        super.onBackPressed()
    }
    fun setListingData(list: List<CouponDataResponseItem>?){
        this.listData=list
    }
    fun getListingData() = listData

    private fun adsAccCurrentFrag() {
        val currentFrag= navController.currentDestination?.id
        val adType=drawerResponse?.data
        when(currentFrag){
            R.id.navHomePage->setAdsByCheck(adType?.homeAdBanner,adType?.homeAdInterstitial)
            R.id.navCoupon->setAdsByCheck(adType?.couponAdBanner,adType?.couponAdInterstitial)
            R.id.navDeal->setAdsByCheck(adType?.dealAdBanner,adType?.dealAdInterstitial)
            R.id.nav_webView->setAdsByCheck("","")
            R.id.navCouponDetail->setAdsByCheck(adType?.couponDetailBanner,"")
            R.id.navDealDetail->setAdsByCheck(adType?.dealDetailBanner,"")
        }
    }
}