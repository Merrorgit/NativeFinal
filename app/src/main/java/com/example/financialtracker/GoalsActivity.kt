package com.example.financialtracker
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialtracker.API.services.GoalService
import com.example.financialtracker.API.services.TransactionService
import com.example.financialtracker.data.CreateGoal
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.financialtracker.data.Goal
import com.example.financialtracker.data.Transaction

class GoalsActivity : AppCompatActivity() {

    private lateinit var GoalInput: EditText
    private lateinit var dateInput: EditText
    private  lateinit var addButton: Button
    // Список категорий (в будущем можно загружать с бэка)
    private val categories = arrayOf("1", "2", "3", "4", "5")
    private var goals : MutableList<Goal> = mutableListOf()
    private lateinit var adapter: GoalGoalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goals) // Убедитесь, что это ваш файл разметки

        GoalInput = findViewById(R.id.goal_input)
        // Выбор категории
        GoalInput.setOnClickListener {
            showCategoryDialog()
        }
        dateInput = findViewById(R.id.goal_deadline_input)
        dateInput.setOnClickListener {
            showDatePickerDialog()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_goals)
        addButton = findViewById(R.id.add_goal_button)
        addButton.setOnClickListener {
            addGoal()
        }

        adapter = GoalGoalAdapter(goals)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        fetchGoals()
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
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                dateInput.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun fetchGoals() {
        GoalService.fetchGoals(this) { fetchedGoals ->
            if (fetchedGoals != null) {
                Log.d("fetchTransactions", "Received transactions: $fetchedGoals")
                goals.clear()
                goals.addAll(fetchedGoals)
                adapter.notifyDataSetChanged()
            } else {
                Log.e("fetchTransactions", "Failed to fetch transactions")
                Toast.makeText(this, "Failed to fetch transactions", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addGoal() {
        val nameInput = findViewById<EditText>(R.id.goal_name_input)
        val deadlineInput = dateInput.text.toString().trim()
        val targetAmountInput = findViewById<EditText>(R.id.goal_targetAmount_input).text.toString().trim().toDouble()

        if (nameInput.text.isEmpty() || deadlineInput.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val newGoal = CreateGoal(
            name = nameInput.text.toString(),
            targetAmount = targetAmountInput,
            deadline = deadlineInput.toString(),
        )
        Log.e("dataCheck", newGoal.toString())
        GoalService.addGoal(this, newGoal) { goal ->
            if (goal != null) {
                Toast.makeText(this, "Transaction added!", Toast.LENGTH_SHORT).show()
                goals.add(0, goal)
                adapter.notifyItemInserted(0)
            } else {
                Toast.makeText(this, "Failed to add transaction", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
