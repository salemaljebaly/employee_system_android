package com.almaki.employeeabsense.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.api.LoginInterface
import com.almaki.employeeabsense.api.ServiceBuilder
import com.almaki.employeeabsense.helper.HandleUI
import com.almaki.employeeabsense.helper.UserPreferences
import com.almaki.employeeabsense.model.response.ProfileResponse
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response
import java.lang.NullPointerException

class HomeFragment : Fragment() , View.OnClickListener{

    lateinit var profileResponse: ProfileResponse
    private lateinit var  userPreferences: UserPreferences
    /** ========================================================================================= */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enable option menu in this fragment
        setHasOptionsMenu(true)
        userPreferences = UserPreferences(requireContext())
    }
    /** ========================================================================================= */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    /** ========================================================================================= */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //    init user pref
        if(userPreferences.getUserAuth().isNotEmpty()){

            getEmployeeInfo(userPreferences.getUserAuth())
        } else {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        view.findViewById<MaterialButton>(R.id.logout).setOnClickListener(this)
        // ---------------------------------------------------------------------------------------- //
        HandleUI.setToolBarFunctions(requireActivity(), getString(R.string.Home), true,
            toolbarVisible = true
        )
        super.onViewCreated(view, savedInstanceState)
    }
    /** ========================================================================================= */
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.logout -> logout()
        }
    }

    /** ========================================================================================= */
    private fun getEmployeeInfo(authorization: String){
        val auth = authorization
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

                    setProfileInfo()
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("response", t.message.toString())
            }

        })

    }

    /** ========================================================================================= */
    private fun setProfileInfo() {
        // todo use shared preference to store profile response object
        try {
            employee_name.text = profileResponse.employee?.emp_full_name
            employee_profile_no.text = profileResponse.employee?.emp_no
            employee_phone.text = profileResponse.employee?.emp_phone
            employee_email.text = profileResponse.employee?.emp_email
            employee_location.text = profileResponse.employee?.emp_location
            employee_occupation.text = profileResponse.employee?.emp_occupation
            employee_birth.text = profileResponse.employee?.emp_birth
        } catch (e : NullPointerException){

        }
    }
    /** ========================================================================================= */
    private fun logout(){
        if(userPreferences.getUserAuth().isNotEmpty()){
            userPreferences.clearStoredData()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }
    /** ========================================================================================= */

}