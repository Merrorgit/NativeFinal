package com.example.financialtracker
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GoalsActivity : AppCompatActivity() {

    private lateinit var GoalInput: EditText
    private lateinit var dateInput: EditText
    // Список категорий (в будущем можно загружать с бэка)
    private val categories = arrayOf("1", "2", "3", "4", "5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goals) // Убедитесь, что это ваш файл разметки

        GoalInput = findViewById(R.id.goal_input)
        // Выбор категории
        GoalInput.setOnClickListener {
            showCategoryDialog()
        }
        dateInput = findViewById(R.id.date_input)
        dateInput.setOnClickListener {
            showDatePickerDialog()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_goals)

        // Пример данных
        val goalsList = listOf(
            GoalGoal("New Laptop", 600, 1000, "10.12.2025"),
            GoalGoal("Vacation", 500, 3000, "01.06.2026"),
            GoalGoal("Car", 700, 1000, "20.09.2025")
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GoalGoalAdapter(goalsList)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_goals // Подсвечивает "Goals"

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_goals -> true // Уже здесь, ничего не делаем
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.nav_transactions -> {
                    startActivity(Intent(this, TransactionsActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    // Открывает диалог со списком категорий
    private fun showCategoryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Сhoose a goal")

        builder.setItems(categories) { _, which ->
            GoalInput.setText(categories[which])
        }

        builder.show()
    }
    // Открывает диалог выбора даты
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dateInput.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }


}
