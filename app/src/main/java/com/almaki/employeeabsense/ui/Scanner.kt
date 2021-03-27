package com.almaki.employeeabsense.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.api.LoginInterface
import com.almaki.employeeabsense.api.ServiceBuilder
import com.almaki.employeeabsense.helper.UserPreferences
import com.almaki.employeeabsense.model.request.CodeRequest
import com.almaki.employeeabsense.model.response.EmployeeDailyRecordResponse
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import retrofit2.Call
import retrofit2.Response

class Scanner : AppCompatActivity() {
    var codeScanner: CodeScanner? = null
    var scannView: CodeScannerView? = null
    var resultData: TextView? = null
    private lateinit var  userPreferences: UserPreferences
    private lateinit var dailyRecordResponse: EmployeeDailyRecordResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)


        userPreferences = UserPreferences(this)
        scannView = findViewById(R.id.scannerView)
        codeScanner = CodeScanner(this, scannView as CodeScannerView)
        resultData = findViewById(R.id.resultsOfQr)
        codeScanner!!.decodeCallback =
            DecodeCallback { result: Result -> runOnUiThread {
                Log.e("decode ", result.text)
                setEmployeeDailyRecord(authorization = userPreferences.getUserAuth(), result.text)
                this.onBackPressed()
            } }
        scannView!!.setOnClickListener { codeScanner!!.startPreview() }
    }

    override fun onResume() {
        super.onResume()
        requestForCamera()
    }

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
    // todo fix the function
    private fun setEmployeeDailyRecord(authorization: String, code_string : String){
        val auth = authorization
        val userInterface : LoginInterface = ServiceBuilder.buildService(LoginInterface::class.java)
        val codeRequest : CodeRequest = CodeRequest(code_string = code_string)
        val loginRequest: Call<EmployeeDailyRecordResponse> = userInterface.addEmployeeDailyRecords(
            auth, codeRequest
        )

        loginRequest.enqueue(object : retrofit2.Callback<EmployeeDailyRecordResponse> {
            override fun onResponse(
                call: Call<EmployeeDailyRecordResponse>,
                response: Response<EmployeeDailyRecordResponse>
            ) {
                Log.e("response", response.code().toString())
                if (response.isSuccessful) {
                    dailyRecordResponse = response.body()!!
                    Log.e("response", dailyRecordResponse.employee )

                }
            }

            override fun onFailure(call: Call<EmployeeDailyRecordResponse>, t: Throwable) {
                Log.e("response", t.message.toString())
            }

        })

    }

    /** ========================================================================================= */
}