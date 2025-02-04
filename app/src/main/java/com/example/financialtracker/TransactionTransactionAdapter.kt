package com.example.financialtracker

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat

class TransactionTransactionAdapter(private val transactions: List<TransactionTransaction>) :
    RecyclerView.Adapter<TransactionTransactionAdapter.TransactionTransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionTransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionTransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionTransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.transactionTitle.text = "Transaction ${position + 1}"
        holder.transactionAmount.text = (if (transaction.isIncome) "+" else "-") + transaction.amount.toString()
        holder.transactionCategory.text = transaction.category
        holder.transactionDate.text = transaction.date

        // Устанавливаем цвет текста для доходов и расходов
        val amountColor = if (transaction.isIncome)
            ContextCompat.getColor(holder.itemView.context, R.color.dark_blue)
        else
            ContextCompat.getColor(holder.itemView.context, R.color.red)

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
