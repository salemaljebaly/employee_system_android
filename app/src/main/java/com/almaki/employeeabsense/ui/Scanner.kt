package com.almaki.employeeabsense.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.almaki.employeeabsense.MainActivity
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.api.LoginInterface
import com.almaki.employeeabsense.api.ServiceBuilder
import com.almaki.employeeabsense.helper.GlobalMembers
import com.almaki.employeeabsense.helper.UserPreferences
import com.almaki.employeeabsense.model.request.CodeRequest
import com.almaki.employeeabsense.model.response.EmployeeDailyRecordResponse
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.google.android.material.button.MaterialButton
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_q_r_code.*
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class Scanner : AppCompatActivity() {
    var codeScanner: CodeScanner? = null
    var scannView: CodeScannerView? = null
    private lateinit var userPreferences: UserPreferences
    private lateinit var dailyRecordResponse: EmployeeDailyRecordResponse

    private lateinit var come_at : MaterialButton
    private lateinit var leave_at : MaterialButton
    private var state: Int = 0
    private var time_remaining : Long = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        /** ---------------------------------------------------------------------------- */
        userPreferences = UserPreferences(this)
        scannView = findViewById(R.id.scannerView)
        codeScanner = CodeScanner(this, scannView as CodeScannerView)
        /** ---------------------------------------------------------------------------- */
        state = intent.getIntExtra("state", 0)
        codeScanner!!.decodeCallback =
            DecodeCallback { result: Result ->
                runOnUiThread {
                    if (state == 0) {
                        setEmployeeDailyRecord(
                            authorization = userPreferences.getUserAuth(),
                            code_string = result.text
                        )
                        GlobalMembers.BTN_COME_AT_STATE = false
                        Log.e("btn before click", GlobalMembers.BTN_COME_AT_STATE.toString())
                        Handler(Looper.getMainLooper()).postDelayed({
                            GlobalMembers.BTN_COME_AT_STATE = true
                            Log.e("btn come at", GlobalMembers.BTN_COME_AT_STATE.toString())
                        }, time_remaining)

                    } else {

                        updateEmployeeDailyRecords(
                            authorization = userPreferences.getUserAuth(),
                            GlobalMembers.DAILY_RECORD_ID,
                            code_string = result.text
                        )
                        GlobalMembers.BTN_LEAVE_AT_STATE = false
                        Log.e("BTN_LEAVE_AT_STATE", GlobalMembers.BTN_LEAVE_AT_STATE.toString())
                        Handler(Looper.getMainLooper()).postDelayed({
                            GlobalMembers.BTN_LEAVE_AT_STATE = true
                            Log.e("BTN_LEAVE_AT_STATE", GlobalMembers.BTN_LEAVE_AT_STATE.toString())
                        }, time_remaining)
                    }

                    this.finish()
                    Toast.makeText(this, "you are register successfully", Toast.LENGTH_SHORT).show()
                }
            }
        scannView!!.setOnClickListener { codeScanner!!.startPreview() }
    }
    /** ========================================================================================= */

    override fun onResume() {
        super.onResume()
        requestForCamera()
    }
    /** ========================================================================================= */
    fun requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    codeScanner!!.startPreview()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(
                        this@Scanner,
                        "Camera Permission is Required.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }


    /** ========================================================================================= */
    private fun setEmployeeDailyRecord(authorization: String, code_string: String) {
        val auth = authorization
        val userInterface: LoginInterface = ServiceBuilder.buildService(LoginInterface::class.java)
        // set current come at time
        val codeRequest: CodeRequest = CodeRequest(code_string = code_string, come_at = getDate())
        val loginRequest: Call<EmployeeDailyRecordResponse> = userInterface.addEmployeeDailyRecords(
            auth, codeRequest
        )

        loginRequest.enqueue(object : retrofit2.Callback<EmployeeDailyRecordResponse> {
            override fun onResponse(
                call: Call<EmployeeDailyRecordResponse>,
                response: Response<EmployeeDailyRecordResponse>
            ) {

                Log.e("set response", response.headers().toString())
                if (response.isSuccessful) {
                    dailyRecordResponse = response.body()!!
                    Log.e("set employee", GlobalMembers.DAILY_RECORD_ID)
                    GlobalMembers.DAILY_RECORD_ID = response.body()!!._id
                    GlobalMembers.DAILY_RECORD_ID = dailyRecordResponse._id
                }
            }

            override fun onFailure(call: Call<EmployeeDailyRecordResponse>, t: Throwable) {
                Log.e("set employee", t.message.toString())
            }

        })

    }

    /** ========================================================================================= */

    /** ========================================================================================= */
    private fun updateEmployeeDailyRecords(
        authorization: String,
        _id: String,
        code_string: String
    ) {
        val auth = authorization
        val userInterface: LoginInterface = ServiceBuilder.buildService(LoginInterface::class.java)
        // set current leave at time
        val codeRequest: CodeRequest = CodeRequest(code_string = code_string, leave_at = getDate())

        val loginRequest: Call<EmployeeDailyRecordResponse> =
            userInterface.updateEmployeeDailyRecords(
                auth, _id, codeRequest
            )

        loginRequest.enqueue(object : retrofit2.Callback<EmployeeDailyRecordResponse> {
            override fun onResponse(
                call: Call<EmployeeDailyRecordResponse>,
                response: Response<EmployeeDailyRecordResponse>
            ) {
                Log.e("update response", response.code().toString())
                Log.e("update response", response.headers().toString())
                if (response.isSuccessful) {
                    dailyRecordResponse = response.body()!!
                    Log.e("update employee", _id)

                }
            }

            override fun onFailure(call: Call<EmployeeDailyRecordResponse>, t: Throwable) {
                Log.e("update response", t.message.toString())
            }

        })

    }

    /** ========================================================================================= */

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("hh:mm:ss")
        return mdformat.format(calendar.time).toString()
    }
}