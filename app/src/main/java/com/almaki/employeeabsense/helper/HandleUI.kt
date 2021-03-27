package com.almaki.employeeabsense.helper

import android.app.Activity
import android.view.View
import com.almaki.employeeabsense.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class HandleUI {
    companion object{
        fun setToolBarFunctions(activity : Activity, title : String, bottomNavVisible : Boolean, toolbarVisible : Boolean) {

            // ---------------------------------------------------------------------- //
            if(toolbarVisible) {
                (activity as MainActivity).toolbar.visibility = View.VISIBLE // show bottom nav
                (activity).toolbar.title = title
            }
            else { (activity as MainActivity).toolbar.visibility = View.GONE } // show bottom nav

            // ---------------------------------------------------------------------- //
            if(bottomNavVisible) {(activity).bottomNavigation.visibility = View.VISIBLE} // show bottom nav
            else {(activity).bottomNavigation.visibility = View.GONE }// show bottom nav
            // ---------------------------------------------------------------------- //

            // ---------------------------------------------------------------------- //
        }
    }
}