package com.almaki.employeeabsense.helper

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(
    val context : Context
) {
    val appContext = context.applicationContext

    private val  sharedPref : SharedPreferences

    /**
     * init its the first function run when get instance from class
     */
    init {
        sharedPref = appContext.getSharedPreferences(
            "user_auth", Context.MODE_PRIVATE
        )
    }

    val edit : SharedPreferences.Editor? = sharedPref.edit()

    /**
     * use to save token in SharedPreferences
     * @param auth string of token
     */
    fun saveData(auth : String){
        edit?.putString("user_auth_token", auth)
        edit?.apply()
        edit?.commit()
    }

    /**
     * use to get stored data depends on key
     * @return return string of token
     */
    fun getUserAuth() : String{
        return sharedPref.getString("user_auth_token", "").toString()
    }

    /**
     * use this function to clear stored data
     */
    fun clearStoredData(){
        edit?.clear()
        edit?.apply()
    }
}