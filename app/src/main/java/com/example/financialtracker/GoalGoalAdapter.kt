package com.example.financialtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalGoalAdapter(private val goals: List<GoalGoal>) :
    RecyclerView.Adapter<GoalGoalAdapter.GoalGoalViewHolder>() {

    class GoalGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_goal_title)
        val progress: TextView = itemView.findViewById(R.id.tv_goal_progress)
        val deadline: TextView = itemView.findViewById(R.id.tv_goal_deadline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalGoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_item, parent, false)
        return GoalGoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalGoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.title.text = goal.title
        holder.progress.text = "${goal.savedAmount}/${goal.targetAmount}"
        holder.deadline.text = "Deadline: ${goal.deadline}"
    }

    override fun getItemCount(): Int = goals.size
}
