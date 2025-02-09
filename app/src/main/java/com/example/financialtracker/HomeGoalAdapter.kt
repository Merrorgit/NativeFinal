package com.example.financialtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financialtracker.data.Goal


class GoalAdapter(private val goals: List<Goal>) :
    RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    class GoalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvGoalName)
        val tvProgress: TextView = view.findViewById(R.id.tvGoalProgress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_goal_item, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.tvName.text = goal.name
        holder.tvProgress.text = "${goal.currentAmount}/${goal.targetAmount}"
    }

    override fun getItemCount(): Int = goals.size
}
