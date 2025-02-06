package com.example.financialtracker.API.routes

import com.example.financialtracker.data.Transaction
import retrofit2.Call
import retrofit2.http.GET

interface TransactionAPI {
    @GET("transactions")
    fun fetchTransactions(): Call<MutableList<Transaction>>?
}