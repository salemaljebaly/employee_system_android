package com.almaki.employeeabsense.helper

import android.content.Context
import android.content.PeriodicSync
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.almaki.employeeabsense.MainActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng

class UserLocation (val context: Context){

    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1010
    var LATITUDE : Double = 0.0
    var LONGTITUDE : Double = 0.0

    /**
     * check location permission
     * */
    fun checkPermission() : Boolean{
        if(ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    /**
     * request location permission from user
     * */
    fun requestPermission(){
        ActivityCompat.requestPermissions(
            (context as MainActivity),
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    /**
     * checking the location service is enabled
     * */
    fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    fun getLastLocation(){
        if(this.checkPermission()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        GlobalMembers.latLag = LatLng(location.latitude, location.longitude)
                    }
                }
            }else{
                Toast.makeText(context,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermission()
        }
    }

    fun getLastLocationAsLatLng() : LatLng{
        var latLng : LatLng = LatLng(0.0, 0.0)
        if(this.checkPermission()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        GlobalMembers.latLag = LatLng(location.latitude, location.longitude)
                        latLng = LatLng(location.latitude, location.longitude)
                    }
                }
            }else{
                Toast.makeText(context,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermission()
        }

        return latLng
    }


    fun NewLocationData(){
        val locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if(checkPermission()){
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,locationCallback,Looper.getMainLooper()
            )
        }
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
        }
    }
}