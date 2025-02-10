package com.example.financialtracker.API.routes

import com.example.financialtracker.data.LoginData

import com.example.financialtracker.data.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthAPI {
    @POST("auth/login")
    fun login(@Body loginData: LoginData): Call<ResponseBody>

    @POST("auth/register")
    fun register(@Body registerData: Map<String, String>): Call<ResponseBody>

    @GET("auth/test")
    fun getName(): Call<UserResponse>
}