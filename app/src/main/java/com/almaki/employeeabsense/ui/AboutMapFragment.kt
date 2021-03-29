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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class AboutMapFragment : Fragment() {
    /** ========================================================================================= */
    private val callback = OnMapReadyCallback { googleMap ->
        /** ------------------------------------------------------------------------------------ */
        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        val high_institute_misrata = LatLng(32.37063287685728, 15.086193059308114)
        googleMap.addMarker(MarkerOptions().position(high_institute_misrata).title(getString(R.string.institute_name)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(high_institute_misrata, 14f))
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