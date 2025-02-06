package com.example.financialtracker.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class Transaction(){
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("category")
    @Expose
    var category: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("dateOfTransaction")
    @Expose
    var date: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null
}
