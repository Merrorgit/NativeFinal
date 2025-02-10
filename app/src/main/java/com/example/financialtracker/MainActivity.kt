package com.example.financialtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialtracker.API.APIService
import com.example.financialtracker.API.services.AuthService
import com.example.financialtracker.API.services.GoalService
import com.example.financialtracker.API.services.TransactionService
import com.example.financialtracker.data.Goal
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.HttpUrl

class MainActivity : AppCompatActivity() {
    private var goals : MutableList<Goal> = mutableListOf()
    private lateinit var balance: TextView
    private lateinit var adapter: GoalAdapter
    private lateinit var UserName: String
    private lateinit var usernameInput: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.homepage)

        // Если куки отсутствуют, переходим на LoginActivity
        if (!isUserLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // --- Код ниже выполнится только если куки есть ---


        val recyclerGoals: RecyclerView = findViewById(R.id.recycler_view_goals)
        recyclerGoals.layoutManager = LinearLayoutManager(this)
        balance = findViewById<TextView>(R.id.balance)
        adapter = GoalAdapter(goals)
        fetchGoals()
        getName()
        usernameInput = findViewById<TextView>(R.id.username_input)

        // Обработка кнопки "Log out"
        val logoutButton: Button = findViewById(R.id.logout)
        logoutButton.setOnClickListener {
            logOut()
        }
        fetchBalance()
        // Настройка нижней навигации
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_home // Подсвечивает "Home"

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true // Уже на главной
                R.id.nav_transactions -> {
                    startActivity(Intent(this, TransactionsActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.nav_goals -> {
                    startActivity(Intent(this, GoalsActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Метод для проверки, есть ли у пользователя сохраненные куки (то есть, залогинен ли он).
     */
    private fun isUserLoggedIn(): Boolean {
        val apiService = APIService.getInstance(this)
        val cookieJar = apiService?.getCookieJar()

        // Проверяем наличие куки
        return cookieJar?.loadForRequest(HttpUrl.get("http://37.151.225.71:5002/"))?.isNotEmpty() == true
    }

    /**
     * Метод для выхода из системы.
     * Очищает куки и перенаправляет на экран логина.
     */
    private fun logOut() {
        val apiService = APIService.getInstance(this)
        val cookieJar = apiService?.getCookieJar()

        // Очищаем все куки
        cookieJar?.clearCookies()

        // Переходим на экран логина
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()  // Завершаем текущую активность, чтобы пользователь не мог вернуться назад
    }
    private fun fetchGoals() {
        GoalService.fetchGoals(this) { fetchedGoals ->
            if (fetchedGoals != null) {
                Log.d("fetchGoals", "Received goals: $fetchedGoals")
                goals.clear()
                goals.addAll(fetchedGoals)
                adapter.notifyDataSetChanged()
            } else {
                Log.e("fetchTransactions", "Failed to fetch transactions")
                Toast.makeText(this, "Failed to fetch transactions", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun fetchBalance() {
        TransactionService.fetchBalance(this) { fetchedBalance ->
            if (fetchedBalance != null) {
                balance.text = fetchedBalance.balance.toString()
            } else {
                Log.e("fetchTransactions", "Failed to fetch transactions")
                Toast.makeText(this, "Failed to fetch transactions", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getName() {
        AuthService.getName(this) { userObj ->
            Log.e("AuthService", userObj?.toString() ?: "userObj is null")

            if (userObj != null) {
                usernameInput.text = "Good day, ${userObj.user.username}!"
            } else {
                Log.e("get name", "Failed to get name")
                Toast.makeText(this, "Failed to get name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}