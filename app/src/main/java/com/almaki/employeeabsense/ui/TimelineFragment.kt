package com.almaki.employeeabsense.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.api.LoginInterface
import com.almaki.employeeabsense.api.ServiceBuilder
import com.almaki.employeeabsense.data.adapter.DailyRecordsAdapter
import com.almaki.employeeabsense.helper.HandleUI
import com.almaki.employeeabsense.helper.UserPreferences
import com.almaki.employeeabsense.model.response.EmployeeDailyRecordResponse
import kotlinx.android.synthetic.main.fragment_timeline.*
import retrofit2.Call
import retrofit2.Response

class TimelineFragment : Fragment() {

    private lateinit var mAdapter: DailyRecordsAdapter
    private var daily_records = ArrayList<EmployeeDailyRecordResponse>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var  userPreferences: UserPreferences
    /** ========================================================================================= */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        userPreferences = UserPreferences(requireContext())
    }
    /** ========================================================================================= */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }
    /** ========================================================================================= */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HandleUI.setToolBarFunctions(requireActivity(), getString(R.string.record_attendance_absence), true,
            toolbarVisible = true
        )
        if(userPreferences.getUserAuth().isNotEmpty()){
            getEmployeeDailyRecords(authorization = userPreferences.getUserAuth())
        } else {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }


    /** ========================================================================================= */
    private fun getEmployeeDailyRecords(authorization: String){
        val auth = authorization
        val employeeInterface : LoginInterface = ServiceBuilder.buildService(LoginInterface::class.java)
        val loginRequest: Call<ArrayList<EmployeeDailyRecordResponse>> = employeeInterface.getEmployeeDailyRecords(
            auth
        )

        loginRequest.enqueue(object : retrofit2.Callback<ArrayList<EmployeeDailyRecordResponse>> {
            override fun onResponse(
                call: Call<ArrayList<EmployeeDailyRecordResponse>>,
                response: Response<ArrayList<EmployeeDailyRecordResponse>>
            ) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        daily_records = response.body()!!
                        mLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        recyclerViewDailyRecords.layoutManager = mLayoutManager
                        mAdapter = DailyRecordsAdapter(daily_records)
                        recyclerViewDailyRecords.adapter = mAdapter
                    }else{
//                        timelineText.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<EmployeeDailyRecordResponse>>, t: Throwable) {
                Log.e("response", t.message.toString())
            }

        })

    }

    /** ========================================================================================= */
}