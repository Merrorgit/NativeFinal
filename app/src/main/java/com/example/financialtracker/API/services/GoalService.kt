package com.example.financialtracker.API.services

import android.content.Context
import android.util.Log
import com.example.financialtracker.API.APIService
import com.example.financialtracker.data.CreateGoal
import com.example.financialtracker.data.Goal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalService private constructor() {
    companion object{
        fun fetchGoals(context: Context?, callback: (MutableList<Goal>?) -> Unit) {
            val apiService = APIService.getInstance(context!!).getGoalApi()
            apiService.fetchGoals()?.enqueue(object : Callback<MutableList<Goal>> {
                override fun onResponse(call: Call<MutableList<Goal>>, response: Response<MutableList<Goal>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("GoalService", "Error: ${response.code()}")
                        callback(mutableListOf())
                    }
                }

                override fun onFailure(call: Call<MutableList<Goal>>, t: Throwable) {
                    Log.e("GoalService", "Network Error: ${t.message}")
                    callback(mutableListOf())
                }
            })

        }
        fun addGoal(context: Context?, goal: CreateGoal, callback: (Goal?) -> Unit) {
            val apiService = APIService.getInstance(context!!).getGoalApi()
            apiService.addGoal(goal).enqueue(object : Callback<Goal> {
                override fun onResponse(call: Call<Goal>, response: Response<Goal>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("TransactionService", "Error: ${response.code()} - ${response.errorBody()?.string()}")

                        callback(null)

                    }
                }

                override fun onFailure(call: Call<Goal>, t: Throwable) {
                    Log.e("TransactionService", "Network Error: ${t.message}")
                    callback(null)
                }
            })
        }

    }
}