package com.almaki.employeeabsense.data

import android.util.Log
import com.almaki.employeeabsense.api.LoginInterface
import com.almaki.employeeabsense.api.ServiceBuilder
import com.almaki.employeeabsense.model.response.ProfileResponse
import retrofit2.Call
import retrofit2.Response

class GetData {
    /** ========================================================================================= */
    companion object{
        lateinit var profileResponse: ProfileResponse
        fun getEmployeeInfo(authorization: String) : ProfileResponse{
                val auth = "Bearer $authorization"
                val userInterface : LoginInterface = ServiceBuilder.buildService(LoginInterface::class.java)

                val loginRequest: Call<ProfileResponse> = userInterface.getEmployeeProfile(
                    auth
                )

                loginRequest.enqueue(object : retrofit2.Callback<ProfileResponse> {
                    override fun onResponse(
                        call: Call<ProfileResponse>,
                        response: Response<ProfileResponse>
                    ) {
                        if (response.isSuccessful) {
                            profileResponse = response.body()!!
                        }
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        Log.e("response", t.message.toString())
                    }

                })
                return profileResponse
            }
    }

    /** ========================================================================================= */
    //    private fun setProfileInfo(firstTextView : TextView, secondTextView : String) {
    //        firstTextView.text = secondTextView
    //    }
    /** ========================================================================================= */
}