package com.example.financialtracker.API.services

import android.content.Context
import android.util.Log
import com.example.financialtracker.API.APIService
import com.example.financialtracker.data.Goal
import com.example.financialtracker.data.LoginData
import com.example.financialtracker.data.User
import com.example.financialtracker.data.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService private constructor(){
    companion object{
        fun logIn(username: String, password: String, context: Context, callback: (Boolean) -> Unit){
            val loginData = LoginData(username, password)
            val apiService = APIService.getInstance(context)?.getAuthAPI()
            apiService?.login(loginData)?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val statusCode = response.code()
                    if (statusCode == 201){
                        callback(true)
                    }
                    else{
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback(false)
                }
            })
        }
        fun getName(context: Context?, callback: (UserResponse) -> Unit) {
            val apiService = APIService.getInstance(context!!).getAuthAPI()
            apiService.getName().enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { callback(it) }
                    } else {
                        Log.e("GoalService", "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("GoalService", "Network Error: ${t.message}")

                }
            })

        }
    }
}