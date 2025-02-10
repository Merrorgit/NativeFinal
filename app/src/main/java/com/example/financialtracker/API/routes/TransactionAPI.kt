package com.example.financialtracker.API.routes

import com.example.financialtracker.data.Balance
import com.example.financialtracker.data.Transaction
import retrofit2.Call
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionAPI {
    @GET("transactions")
    fun fetchTransactions(): Call<MutableList<Transaction>>

    @GET("balance")
    fun fetchBalance(): Call<Balance>

    @POST("transactions")
    fun addTransaction(@Body transaction: Transaction): Call<Transaction>
}
