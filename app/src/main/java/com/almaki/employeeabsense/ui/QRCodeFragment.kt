package com.almaki.employeeabsense.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.helper.GlobalMembers
import com.almaki.employeeabsense.helper.HandleUI
import com.almaki.employeeabsense.helper.UserLocation
import com.almaki.employeeabsense.helper.UserPreferences
import kotlinx.android.synthetic.main.fragment_q_r_code.*


class QRCodeFragment : Fragment() {
    private lateinit var  userPreferences: UserPreferences

    /** ========================================================================================= */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        /** ----------------------------------------------------------------------------------- */
        userPreferences = UserPreferences(requireContext())
        /** ----------------------------------------------------------------------------------- */
    }
    /** ========================================================================================= */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_code, container, false)
    }
    /** ========================================================================================= */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val state : Boolean = userPreferences.getUserBuildingState()
        if(!state){
            btn_come_at.isEnabled = false
            btn_leave_at.isEnabled = false
            Toast.makeText(
                requireContext(),
                getString(R.string.cant_register_out_of_buildig),
                Toast.LENGTH_SHORT
            ).show()
        }

        if(!GlobalMembers.BTN_COME_AT_STATE){
            btn_come_at.isEnabled = false
        }

        if(!GlobalMembers.BTN_LEAVE_AT_STATE){
            btn_leave_at.isEnabled = false
        }

            btn_come_at.setOnClickListener{
                val intent = Intent(requireContext().applicationContext,
                    Scanner::class.java)
                intent.putExtra("state", 0)
                startActivity(intent)
            }

            btn_leave_at.setOnClickListener{
                val intent = Intent(requireContext().applicationContext,
                    Scanner::class.java)
                intent.putExtra("state", 1)
                startActivity(intent)
            }

        Log.e("onViewCreated", userPreferences.getUserBuildingState().toString())

        HandleUI.setToolBarFunctions(
            requireActivity(), getString(R.string.attendance_registration), true,
            toolbarVisible = true
        )
    }


    /** =========================================================================================
     * get current location for user
     *
     * */
    private fun getUserLocation() {
        val userLocation: UserLocation = UserLocation(requireContext())
        Log.e("", userLocation.checkPermission().toString())
        Log.e("", userLocation.isLocationEnabled().toString())
        userLocation.requestPermission()
        userLocation.getLastLocation()

    }

    override fun onStart() {
        getUserLocation()
        super.onStart()
    }

}