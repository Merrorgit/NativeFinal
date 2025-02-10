package com.example.financialtracker.data

data class Goal(
    val name: String,
    val currentAmount: Double,
    val targetAmount: Double,
    val deadline: String
)

data class CreateGoal(
    val name: String,
    val targetAmount: Double,
    val deadline: String
)