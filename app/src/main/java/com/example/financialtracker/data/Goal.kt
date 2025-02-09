package com.example.financialtracker.data

data class Goal(
    val name: String,
    val currentAmount: Int,
    val targetAmount: Int,
    val deadline: String
)

data class CreateGoal(
    val name: String,
    val targetAmount: Int,
    val deadline: String
)