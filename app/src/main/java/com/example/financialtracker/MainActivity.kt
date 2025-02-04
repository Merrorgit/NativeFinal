package com.example.financialtracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val transactions = listOf(
            Transaction("Transaction 1", 500.0),
            Transaction("Transaction 2", -667.0),
            Transaction("Transaction 3", 42.0),
            Transaction("Transaction 4", 0.0),
            Transaction("Transaction 5", -0.0)
        )
        val goals = listOf(
            Goal("Goal 1", 600, 1000),
            Goal("Goal 2", 500, 3000),
            Goal("Goal 3", 700, 1000)
        )

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_transactions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TransactionAdapter(transactions)

        val recyclerGoals: RecyclerView = findViewById(R.id.recycler_view_goals)
        recyclerGoals.layoutManager = LinearLayoutManager(this)
        recyclerGoals.adapter = GoalAdapter(goals)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_home // Подсвечивает "Home"

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true // Уже здесь, ничего не делаем
                R.id.nav_transactions -> {
                    startActivity(Intent(this, TransactionsActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_goals -> {
                    startActivity(Intent(this, GoalsActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}