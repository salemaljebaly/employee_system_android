package com.almaki.employeeabsense.helper

import android.app.Application
import com.google.android.gms.maps.model.LatLng

class GlobalMembers : Application() {
    companion object{
        var DAILY_RECORD_ID : String = ""
        var latLag : LatLng = LatLng(0.0, 0.0)
        var IS_IN_EMPLOYEE_BUILDING : Boolean = false
        var BTN_COME_AT_STATE : Boolean = true
        var BTN_LEAVE_AT_STATE : Boolean = true

    }
}