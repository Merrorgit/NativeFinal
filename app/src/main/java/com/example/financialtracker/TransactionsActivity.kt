package com.example.financialtracker
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialtracker.API.services.TransactionService
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.financialtracker.data.Transaction
import com.example.financialtracker.data.TransactionType

class TransactionsActivity : AppCompatActivity() {

    private lateinit var categoryInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var adapter: TransactionAdapter
    private lateinit var addButton: Button
    private lateinit var radioGroup : RadioGroup
    // Список категорий (в будущем можно загружать с бэка)
    private val categories = arrayOf("Food", "Transport", "Shopping", "Bills", "Entertainment")
    private var transactions: MutableList<Transaction> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transactions) // Убедитесь, что это ваш файл разметки

        categoryInput = findViewById(R.id.category_input)
        dateInput = findViewById(R.id.date_input)
        addButton = findViewById(R.id.add_transaction_button)
        radioGroup  = findViewById(R.id.radio_group_transaction)
        // Выбор категории
        categoryInput.setOnClickListener {
            showCategoryDialog()
        }

        // Выбор даты
        dateInput.setOnClickListener {
            showDatePickerDialog()
        }
        addButton.setOnClickListener {
            addTransaction()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_transactions)
        recyclerView.layoutManager = LinearLayoutManager(this)



        adapter = TransactionAdapter(transactions)
        recyclerView.adapter = adapter
        fetchTransactions()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_transactions // Подсвечивает "Transactions"

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_transactions -> true // Уже здесь, ничего не делаем
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
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
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                dateInput.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun fetchTransactions() {
        TransactionService.fetchTransactions(this) { fetchedTransactions ->
            if (fetchedTransactions != null) {
                Log.d("fetchTransactions", "Received transactions: $fetchedTransactions")
                transactions.clear()
                transactions.addAll(fetchedTransactions)
                adapter.notifyDataSetChanged()
            } else {
                Log.e("fetchTransactions", "Failed to fetch transactions")
                Toast.makeText(this, "Failed to fetch transactions", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to get selected transaction type
    private fun getSelectedTransactionType(radioGroup: RadioGroup): TransactionType {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.radio_income -> TransactionType.INCOME
            else -> TransactionType.EXPENSE
        }
    }


    private fun addTransaction() {
        val nameInput = findViewById<EditText>(R.id.name_input)
        val amountInput = findViewById<EditText>(R.id.amount_input)
        val category = categoryInput.text.toString()
        val date = dateInput.text.toString().trim()
        val amount = amountInput.text.toString().toDoubleOrNull()
        val type = getSelectedTransactionType(radioGroup)

        if (nameInput.text.isEmpty() || amount == null || category.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }



        val newTransaction = Transaction(
            name = nameInput.text.toString(),
            amount = amount,
            category = category.toString(),
            dateOfTransaction = date.toString(),
            type = type
        )
        TransactionService.addTransaction(this, newTransaction) { transaction ->
            if (transaction != null) {
                Toast.makeText(this, "Transaction added!", Toast.LENGTH_SHORT).show()
                transactions.add(0, transaction)
                adapter.notifyItemInserted(0)
            } else {
                Toast.makeText(this, "Failed to add transaction", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
