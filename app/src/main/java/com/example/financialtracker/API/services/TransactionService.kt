package com.example.financialtracker.API.services

import android.content.Context
import android.util.Log
import com.example.financialtracker.API.APIService
import com.example.financialtracker.data.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class TransactionService private constructor() {
    companion object{
        fun fetchTransactions(context: Context?, callback: (MutableList<Transaction>?) -> Unit) {
            val apiService = APIService.getInstance(context!!)?.getTransactionAPI()
            apiService?.fetchTransactions()?.enqueue(object : Callback<MutableList<Transaction>> {
                override fun onResponse(call: Call<MutableList<Transaction>>, response: Response<MutableList<Transaction>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("TransactionService", "Error: ${response.code()}")
                        callback(mutableListOf())
                    }
                }

                override fun onFailure(call: Call<MutableList<Transaction>>, t: Throwable) {
                    Log.e("TransactionService", "Network Error: ${t.message}")
                    callback(mutableListOf())
                }
            })
        }
    }
}