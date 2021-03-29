package com.almaki.employeeabsense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.almaki.employeeabsense.helper.UserLocation
import com.almaki.employeeabsense.helper.UserPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //=============================
    lateinit var toolbar: Toolbar
    lateinit var userPreferences : UserPreferences // use to check if user is logged in or not
    lateinit var bottomNavigation : BottomNavigationView // implement bottom navigation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // save employee auth
//        userPreferences = UserPreferences(this)
        toolbar = findViewById(R.id.toolbar)
        bottomNavigation = findViewById(R.id.bottomNavigationView)
        // set nav controller
        val navController = findNavController(R.id.nav_host_fragment)
        // navigate to fragment with where id = fragment name
        bottomNavigation.setupWithNavController(navController)
        getUserLocation()
    }


    /** =========================================================================================
     * get current location for user
     *
     * */
    private fun getUserLocation() {
        val userLocation: UserLocation = UserLocation(this)
        Log.e("", userLocation.checkPermission().toString())
        Log.e("", userLocation.isLocationEnabled().toString())
        userLocation.requestPermission()
        userLocation.getLastLocation()

    }
    /** ========================================================================================= */
}