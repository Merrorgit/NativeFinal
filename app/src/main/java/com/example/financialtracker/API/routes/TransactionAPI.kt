package com.example.financialtracker.API.routes

import com.example.financialtracker.data.Transaction
import retrofit2.Call
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionAPI {
    @GET("transactions")
    fun fetchTransactions(): Call<MutableList<Transaction>>

    @POST("transactions")
    fun addTransaction(@Body transaction: Transaction): Call<Transaction>
}
