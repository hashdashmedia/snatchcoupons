package com.app.couponapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.app.couponapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController
    private lateinit var mAppBarConfiguration:AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusActionBar()
        setUpDrawer()
        setDrawerListener()
    }
   fun setUpDrawer(){
       setSupportActionBar(binding.contentMainLayout.appBarLayout.toolbar)
       navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
       mAppBarConfiguration = AppBarConfiguration.Builder(
           navController.graph)
           .setOpenableLayout(binding.drawerLayout)
           .build()
       setupActionBarWithNavController(this, navController, mAppBarConfiguration)
       setupWithNavController(binding.navigationView, navController)

   }
    fun setDrawerListener(){
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){ }
            supportActionBar?.setTitle("")
        }
        binding.tvOptionOne.setOnClickListener {
            navController.navigate(R.id.nav_OptionOne)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp())
    }
}