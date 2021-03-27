package com.almaki.employeeabsense.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.api.LoginInterface
import com.almaki.employeeabsense.api.ServiceBuilder
import com.almaki.employeeabsense.helper.HandleUI
import com.almaki.employeeabsense.helper.MakaniSnackbar
import com.almaki.employeeabsense.helper.UserPreferences
import com.almaki.employeeabsense.helper.Validate
import com.almaki.employeeabsense.model.request.LoginRequestModel
import com.almaki.employeeabsense.model.response.LoginSuccessResponse
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Response


class LoginFragment : Fragment() , View.OnClickListener {

    lateinit var  validate : Validate
    lateinit var userPreferences : UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        userPreferences = UserPreferences(requireContext())

        userPreferences = UserPreferences(requireContext())
    }
    /** ========================================================================================= */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // set title to fragment
        HandleUI.setToolBarFunctions(requireActivity(), getString(R.string.login),
            bottomNavVisible = false,
            toolbarVisible = false
        )

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    /** ========================================================================================= */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // create instance from validate class
        validate = Validate(requireView(), requireActivity())
         view.findViewById<TextInputEditText>(R.id.employee_username).setOnClickListener(this)
         view.findViewById<TextInputEditText>(R.id.employee_password).setOnClickListener(this)
         view.findViewById<MaterialButton>(R.id.employee_signin).setOnClickListener(this)
    }


    /** ========================================================================================= */
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.employee_signin -> {
                val employee_no = view?.findViewById<TextInputEditText>(R.id.employee_username)?.text.toString()
                val employee_password = view?.findViewById<TextInputEditText>(R.id.employee_password)?.text.toString()
                if (validate.validateLogin(
                        employee_no,
                        employee_password,
                    )
                ) {
                    loginToEmployees(employee_no, employee_password)
                }
            }

        }
    }
    /** ========================================================================================= */

    private fun loginToEmployees(employee_no: String, employee_password: String){
        val loginRequestModel = LoginRequestModel(
            emp_no = employee_no,
            emp_password = employee_password
        )

        Log.e("loginToEmployees ",  "start")
        val loginInterface : LoginInterface = ServiceBuilder.buildService(LoginInterface::class.java)

        val loginRequest: Call<LoginSuccessResponse> = loginInterface.loginToEmployee(
            loginRequestModel
        )

        var userAuth = ""
        /** ------------------------------------------------------------------------------------- //
         * Apis calls
         */
        loginRequest.enqueue(object : retrofit2.Callback<LoginSuccessResponse> {
            override fun onResponse(
                    call: Call<LoginSuccessResponse>,
                    response: Response<LoginSuccessResponse>
            ) {


                Log.e("response ",  response.code().toString())
                if (response.isSuccessful) {
                    userPreferences.saveData(response.body()!!.token.toString())
                    userAuth = response.body()!!.token.toString()
                    Log.e("response ",  userAuth)
                    val bundle = bundleOf("getUserAuth" to userAuth)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)
                } else {

                    // Set error text
//                    makani_username.error = getString(R.string.error)

                    // Clear error text
//                    makani_username.error = null
                }
                MakaniSnackbar.showMsg(
                        requireView(),
                        requireActivity(),
                        getString(R.string.invalid_credentials),
                        "",
                        R.color.snack_bar_error_color
                )
            }

            override fun onFailure(call: Call<LoginSuccessResponse>, t: Throwable) {
                Log.e("response", t.message.toString())
            }

        })
        /** ------------------------------------------------------------------------------------- */

    }
    /** ========================================================================================= */


}