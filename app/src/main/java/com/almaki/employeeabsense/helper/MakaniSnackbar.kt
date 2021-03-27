package com.almaki.employeeabsense.helper

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar

class MakaniSnackbar {

    companion object{
        fun showMsg(view: View, activity : FragmentActivity, message: String , actiontext : String, snackColor: Int){
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
            // To change background color to red
            snackbar.view.setBackgroundColor(ContextCompat.getColor(activity,snackColor))
            // To change color for action button
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.duration = 2000
            snackbar.setAction(actiontext){
//                fun onClick(v: View) {
//
//                }
            }.show()
        }
    }

}