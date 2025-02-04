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

class TransactionsActivity : AppCompatActivity() {

    private lateinit var categoryInput: EditText
    private lateinit var dateInput: EditText

    // Список категорий (в будущем можно загружать с бэка)
    private val categories = arrayOf("Food", "Transport", "Shopping", "Bills", "Entertainment")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transactions) // Убедитесь, что это ваш файл разметки

        categoryInput = findViewById(R.id.category_input)
        dateInput = findViewById(R.id.date_input)

        // Выбор категории
        categoryInput.setOnClickListener {
            showCategoryDialog()
        }

        // Выбор даты
        dateInput.setOnClickListener {
            showDatePickerDialog()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_transactions)
        recyclerView.layoutManager = LinearLayoutManager(this)

// Создаем пример транзакций
        val transactions = listOf(
            TransactionTransaction(500, "Salary", "24.10.2025", true),
            TransactionTransaction(500, "Gift", "09.11.2025", false),
            TransactionTransaction(500, "Salary", "24.10.2025", true),
            TransactionTransaction(500, "Salary", "24.10.2025", true)
        )

        val adapter = TransactionTransactionAdapter(transactions)
        recyclerView.adapter = adapter


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_transactions // Подсвечивает "Transactions"

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_transactions -> true // Уже здесь, ничего не делаем
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
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

    // Открывает диалог со списком категорий
    private fun showCategoryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a Category")

        builder.setItems(categories) { _, which ->
            categoryInput.setText(categories[which])
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
