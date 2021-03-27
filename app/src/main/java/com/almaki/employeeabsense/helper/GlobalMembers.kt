package com.almaki.employeeabsense.helper

import android.app.Application

class GlobalMembers : Application() {
    companion object{
        const val PRIMARY_INFOMATION = 0
        const val FINAL_CHECK = 1
        const val CHECK_POI = 2
        const val ADD_POI = 3
        var DAILY_RECORD_ID : String = ""

    }
}