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

    val user_edit : SharedPreferences.Editor? = sharedPref.edit()
    val building_state_edit : SharedPreferences.Editor? = sharedPref.edit()
    val come_at_btn_state : SharedPreferences.Editor? = sharedPref.edit()
    val leave_at_btn_sata : SharedPreferences.Editor? = sharedPref.edit()

    /**
     * use to save token in SharedPreferences
     * @param auth string of token
     */
    fun saveData(auth : String){
        user_edit?.putString("user_auth_token", auth)
        user_edit?.apply()
        user_edit?.commit()
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
        user_edit?.clear()
        user_edit?.apply()
    }
    /**
     * use to save token in SharedPreferences
     * @param state check if user inside the building or not
     */
    fun saveBuildingState(state : Boolean){
        building_state_edit?.putBoolean("user_building_state", state)
        building_state_edit?.apply()
        building_state_edit?.commit()
    }

    /**
     * use to get building user state
     * @return return user building state
     */
    fun getUserBuildingState() : Boolean{
        return sharedPref.getBoolean("user_building_state", false)
    }

    /**
     * use this function to clear UserBuildingState(
     */
    fun clearUserBuildingState(){
        building_state_edit?.clear()
        building_state_edit?.apply()
    }


    /**
     * use to save token in SharedPreferences
     * @param state check if user inside the building or not
     */
    fun saveBtnComeAt(state : Boolean){
        come_at_btn_state?.putBoolean("btn_come_at_state", state)
        come_at_btn_state?.apply()
        come_at_btn_state?.commit()
    }

    /**
     * use to get building user state
     * @return return user building state
     */
    fun getBtnComeAt() : Boolean{
        return sharedPref.getBoolean("btn_come_at_state", true)
    }

    /**
     * use this function to clear UserBuildingState(
     */
    fun clearBtnComeAt(){
        come_at_btn_state?.clear()
        come_at_btn_state?.apply()
    }

    /**
     * use to save token in SharedPreferences
     * @param state check if user inside the building or not
     */
    fun saveBtnLeaveAt(state : Boolean){
        leave_at_btn_sata?.putBoolean("btn_leave_at_state", state)
        leave_at_btn_sata?.apply()
        leave_at_btn_sata?.commit()
    }

    /**
     * use to get building user state
     * @return return user building state
     */
    fun getBtnLeaveAt() : Boolean{
        return sharedPref.getBoolean("btn_leave_at_state", true)
    }

    /**
     * use this function to clear UserBuildingState(
     */
    fun clearBtnLeaveAt(){
        leave_at_btn_sata?.clear()
        leave_at_btn_sata?.apply()
    }
}