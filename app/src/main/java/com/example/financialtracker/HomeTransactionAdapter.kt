package com.example.financialtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Transaction(val name: String, val amount: Double)

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvTransactionName)
        val tvAmount: TextView = view.findViewById(R.id.tvTransactionAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.tvName.text = transaction.name
        holder.tvAmount.text = if (transaction.amount >= 0) "+${transaction.amount}" else transaction.amount.toString()

        // Меняем цвет текста в зависимости от суммы
        holder.tvAmount.setTextColor(
            if (transaction.amount < 0) holder.itemView.context.getColor(R.color.red)
            else holder.itemView.context.getColor(R.color.dark_blue)
        )
    }

    override fun getItemCount(): Int = transactions.size
}
