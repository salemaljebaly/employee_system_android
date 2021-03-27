package com.almaki.employeeabsense.api

import com.almaki.employeeabsense.model.request.CodeRequest
import com.almaki.employeeabsense.model.request.LoginRequestModel
import com.almaki.employeeabsense.model.response.EmployeeDailyRecordResponse
import com.almaki.employeeabsense.model.response.LoginSuccessResponse
import com.almaki.employeeabsense.model.response.ProfileResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginInterface {

    /** ------------------------------------------------------------------------------------------//
     * @param loginRequestModel object passed to to endpoint
     * @return LoginSuccessResponse object
     * ------------------------------------------------------------------------------------------ */
    @POST("api/employees/auth")
    fun loginToEmployee(@Body loginRequestModel: LoginRequestModel): Call<LoginSuccessResponse>


    /** ------------------------------------------------------------------------------------------//
     * @param authorization ist a token passed to system and employee info
     * @return LoginSuccessResponse object
     * ------------------------------------------------------------------------------------------ */
    @GET("api/employees/")
    fun getEmployeeProfile(@Header("x-auth-token") authorization: String): Call<ProfileResponse>

    /** ------------------------------------------------------------------------------------------//
     * @param authorization ist a token passed to system and employee info
     * @return LoginSuccessResponse object
     * ------------------------------------------------------------------------------------------ */
    @POST("api/dailyrecords/")
    fun addEmployeeDailyRecords(
        @Header("x-auth-token") authorization: String,
        @Body codeRequest: CodeRequest
    ): Call<EmployeeDailyRecordResponse>




    /** ------------------------------------------------------------------------------------------//
     * @param authorization ist a token passed to system to get all daily records
     * @return LoginSuccessResponse object
     * ------------------------------------------------------------------------------------------ */
    @GET("api/dailyrecords/")
    fun getEmployeeDailyRecords(@Header("x-auth-token") authorization: String): Call<ArrayList<EmployeeDailyRecordResponse>>
}