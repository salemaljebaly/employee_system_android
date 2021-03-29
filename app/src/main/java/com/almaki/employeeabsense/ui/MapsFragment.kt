package com.almaki.employeeabsense.ui

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.helper.GlobalMembers
import com.almaki.employeeabsense.helper.UserLocation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {
    var currentSelect: Marker? = null
    /** ========================================================================================= */
    private val callback = OnMapReadyCallback { googleMap ->
        /** ------------------------------------------------------------------------------------ */
        currentSelect = googleMap.addMarker(
            MarkerOptions().position(GlobalMembers.latLag).title("current place")
        )


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GlobalMembers.latLag, 12f))

        val mapCircle : Circle = googleMap.addCircle(
            CircleOptions().center(LatLng(32.36565153088628, 15.16056886316602))
                .radius(1000.0)
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


        Log.e("IS_IN_EMPLOYEE_BUILDING", GlobalMembers.IS_IN_EMPLOYEE_BUILDING.toString())
//        val high_institute_misrata = LatLng(32.37063287685728, 15.086193059308114)
//        googleMap.addMarker(MarkerOptions().position(high_institute_misrata).title(getString(R.string.institute_name)))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(high_institute_misrata))
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
}