package com.example.frontcapstone2025.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @GET("/sambyeon/get_positiion")
    suspend fun getWifiPosition(
        @Query("origin") origin: Double,
        @Query("origin_right") origin_right: Double,
        @Query("origin_cross_one") origin_cross_one: Double,
        @Query("origin_cross_two") origin_cross_two: Double,
        @Query("one_side_length") one_side_length: Double,
        @Query("knee_to_eyes") knee_to_eyes: Double,
    ): Response<WifiPosition>

    @Multipart
    @POST("/pcap/suspicious")
    suspend fun analyzePcap(
        @Part file: MultipartBody.Part,
    ): Response<StreamResponse>
}