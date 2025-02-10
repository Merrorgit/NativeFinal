package com.example.financialtracker.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("login")
    @Expose
    var login: String? = null,

    @SerializedName("password")
    @Expose
    var password: String? = null
)

data class UserResponse(
    val user: User
)

data class User(
    val username: String
)
