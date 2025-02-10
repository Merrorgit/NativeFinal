package com.example.financialtracker

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.financialtracker.data.Transaction
import com.example.financialtracker.data.TransactionType
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class TransactionAdapter(private val transactions: MutableList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionTransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionTransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionTransactionViewHolder(view)
    }
    private fun formatDate(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) // Example: 10 Feb 2025, 15:25
            val date = inputFormat.parse(isoDate)

            date?.let { outputFormat.format(it) } ?: "Invalid date"
        } catch (e: Exception) {
            "Invalid date"
        }
    }
    override fun onBindViewHolder(holder: TransactionTransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.transactionTitle.text = "Transaction ${position + 1}"

        // Check if the transaction is an income or expense
        val isIncome = transaction.type == TransactionType.INCOME

        // Update amount text with "+" or "-" sign
        holder.transactionAmount.text = (if (isIncome) "+" else "-") + transaction.amount.toString()
        holder.transactionCategory.text = transaction.category

        // Format transaction date before displaying
        val formattedDate = formatDate(transaction.dateOfTransaction)
        holder.transactionDate.text = formattedDate

        // Set text color based on type
        val amountColor = if (isIncome)
            ContextCompat.getColor(holder.itemView.context, R.color.dark_blue) // Income: blue
        else
            ContextCompat.getColor(holder.itemView.context, R.color.red) // Expense: red

        holder.transactionAmount.setTextColor(amountColor)
    }


    override fun getItemCount() = transactions.size

    class TransactionTransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionTitle: TextView = itemView.findViewById(R.id.transaction_title)
        val transactionAmount: TextView = itemView.findViewById(R.id.transaction_amount)
        val transactionCategory: TextView = itemView.findViewById(R.id.transaction_category)
        val transactionDate: TextView = itemView.findViewById(R.id.transaction_date)
    }
}
