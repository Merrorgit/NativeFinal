package com.example.financialtracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialtracker.API.APIService
import com.example.financialtracker.API.services.AuthService
import com.example.financialtracker.API.services.TransactionService
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.HttpUrl
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        // Если куки отсутствуют, переходим на LoginActivity
        if (!isUserLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // --- Код ниже выполнится только если куки есть ---
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

        // Обработка кнопки "Log out"
        val logoutButton: Button = findViewById(R.id.logout)
        logoutButton.setOnClickListener {
            logOut()
        }

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
}