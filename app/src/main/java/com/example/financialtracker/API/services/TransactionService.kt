package com.example.financialtracker.API.services

import android.content.Context
import android.util.Log
import com.example.financialtracker.API.APIService
import com.example.financialtracker.data.Balance
import com.example.financialtracker.data.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionService private constructor() {
    companion object{
        fun fetchTransactions(context: Context?, callback: (MutableList<Transaction>?) -> Unit) {
            val apiService = APIService.getInstance(context!!).getTransactionAPI()
            apiService.fetchTransactions()?.enqueue(object : Callback<MutableList<Transaction>> {
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

        fun addTransaction(context: Context?, transaction: Transaction, callback: (Transaction?) -> Unit) {
            val apiService = APIService.getInstance(context!!).getTransactionAPI()
            apiService.addTransaction(transaction).enqueue(object : Callback<Transaction> {
                override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("TransactionService", "Error: ${response.code()} - ${response.errorBody()?.string()}")

                        callback(null)

                    }
                }

                override fun onFailure(call: Call<Transaction>, t: Throwable) {
                    Log.e("TransactionService", "Network Error: ${t.message}")
                    callback(null)
                }
            })
        }

        fun fetchBalance(context: Context?, callback: (Balance?) -> Unit) {
            val apiService = APIService.getInstance(context!!).getTransactionAPI()
            apiService.fetchBalance()?.enqueue(object : Callback<Balance> {
                override fun onResponse(call: Call<Balance>, response: Response<Balance>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("TransactionService", "Error: ${response.code()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<Balance>, t: Throwable) {
                    Log.e("TransactionService", "Network Error: ${t.message}")
                    callback(null)
                }
            })
        }
    }
}