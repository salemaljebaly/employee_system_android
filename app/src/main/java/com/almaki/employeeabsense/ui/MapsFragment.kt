package com.almaki.employeeabsense.ui

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.helper.GlobalMembers
import com.almaki.employeeabsense.helper.UserLocation
import com.almaki.employeeabsense.helper.UserPreferences
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {
    var currentSelect: Marker? = null
    lateinit var userPreferences: UserPreferences
    /** ========================================================================================= */
    private val callback = OnMapReadyCallback { googleMap ->
        /** ------------------------------------------------------------------------------------ */
        getUserLocation()
        currentSelect = googleMap.addMarker(
            MarkerOptions().position(GlobalMembers.latLag).title("current place")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GlobalMembers.latLag, 12f))
        // 32.369850459699116, 15.086221852647832 | 32.36412808975193, 15.160476803384258
        val targetLatLng : LatLng = LatLng(32.369850459699116, 15.086221852647832)
        val mapCircle : Circle = googleMap.addCircle(
            CircleOptions().center(targetLatLng)
                .radius(400.0)
                .strokeColor(Color.RED)
        )
        val distance = FloatArray(2)
        Location.distanceBetween(
            GlobalMembers.latLag.latitude,
            GlobalMembers.latLag.longitude,
            mapCircle.center.latitude,
            mapCircle.center.longitude,
            distance
        )

        GlobalMembers.IS_IN_EMPLOYEE_BUILDING = distance[0] <= mapCircle.radius
        userPreferences.saveBuildingState(distance[0] <= mapCircle.radius)

    }

    /** ========================================================================================= */
    override fun onCreate(savedInstanceState: Bundle?) {
        userPreferences = UserPreferences(requireContext())
        super.onCreate(savedInstanceState)
    }
    /** ========================================================================================= */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }
    /** ========================================================================================= */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    /** =========================================================================================
     * get current location for user
     *
     * */
    private fun getUserLocation(){
        val userLocation: UserLocation = UserLocation(requireContext())
        Log.e("", userLocation.checkPermission().toString())
        Log.e("", userLocation.isLocationEnabled().toString())
        userLocation.requestPermission()
        userLocation.getLastLocation()
    }
}