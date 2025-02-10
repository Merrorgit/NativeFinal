package com.example.financialtracker.data

import com.google.gson.annotations.SerializedName

enum class TransactionType {
    @SerializedName("INCOME")
    INCOME,

    @SerializedName("EXPENSE")
    EXPENSE
}

data class Transaction(
     val name: String,
     val type: TransactionType,
     var category: String,
     var amount: Double,
     var dateOfTransaction: String,
)

data class Balance(
    val balance: Double,
)