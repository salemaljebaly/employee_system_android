package com.almaki.employeeabsense.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {


    private const val USER_URL : Int = 0
    private const val PROFILE_URL : Int = 1
    // todo change link from development to production link https://warm-retreat-12818.herokuapp.com/
    private const val BASE_URL : String = "https://warm-retreat-12818.herokuapp.com/"

    // Create OkHttpClient
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()

    // Create Retrofit Builder
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // Create Retrofit Instance
    private val retrofit : Retrofit = builder.build()

    /** ----------------------------------------------------------------------------------------- //
     * Get instance by generic type
     * @param serviceType pass serviceType and create retrofit object  */
    fun <T> buildService(serviceType : Class<T>): T {
        return retrofit.create(serviceType)
    }
    /** ----------------------------------------------------------------------------------------- */
}