package com.example.financialtracker.API.routes

import com.example.financialtracker.data.CreateGoal
import com.example.financialtracker.data.Goal
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GoalAPI {
    @GET("goals")
    fun fetchGoals(): Call<MutableList<Goal>>

    @POST("goals")
    fun addGoal(@Body goal: CreateGoal): Call<Goal>
}
