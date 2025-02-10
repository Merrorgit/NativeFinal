package com.example.financialtracker


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financialtracker.data.Goal
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class GoalGoalAdapter(private val goals: MutableList<Goal>) :
    RecyclerView.Adapter<GoalGoalAdapter.GoalGoalViewHolder>() {

    class GoalGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_goal_title)
        val progress: TextView = itemView.findViewById(R.id.tv_goal_progress)
        val deadline: TextView = itemView.findViewById(R.id.tv_goal_deadline)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalGoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_item, parent, false)
        return GoalGoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalGoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.title.text = goal.name
        holder.progress.text = "${goal.currentAmount}/${goal.targetAmount}"

        // Convert deadline to a readable format
        val formattedDeadline = formatDate(goal.deadline)
        holder.deadline.text = "$formattedDeadline"
    }

    override fun getItemCount(): Int = goals.size
}
