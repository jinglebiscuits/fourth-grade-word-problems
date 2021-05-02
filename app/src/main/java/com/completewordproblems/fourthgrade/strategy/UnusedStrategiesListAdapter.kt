package com.completewordproblems.fourthgrade.strategy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.Wizard
import com.completewordproblems.fourthgrade.models.Strategy

class UnusedStrategiesListAdapter(private val onStrategyAddedListener: OnStrategyAddedListener) :
    RecyclerView.Adapter<UnusedStrategiesListAdapter.UnusedItemViewHolder>() {

    val strategies = arrayListOf(
        Strategy.READ_THE_PROBLEM,
        Strategy.INSPECT_KEY_WORDS,
        Strategy.WHAT_ARE_YOU_LOOKING_FOR,
        Strategy.WHAT_INFORMATION_IS_NEEDED,
        Strategy.DRAW_THE_SCENE,
        Strategy.WRITE_THE_EQUATION,
        Strategy.SOLVE_THE_PROBLEM
    )

    private val unusedStrategies = strategies.filter {
        val currentStrategies = Wizard.currentStudent?.strategies ?: listOf()
        !currentStrategies.contains(it)
    } as ArrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnusedItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.unused_strategy_layout, parent, false)
        val itemViewHolder = UnusedItemViewHolder(view)
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: UnusedItemViewHolder, position: Int) {
        holder.textView.text = unusedStrategies[position].displayText
        holder.addButton.setOnClickListener {
            onStrategyAddedListener.onStrategyAdded(unusedStrategies[position])
            unusedStrategies.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return unusedStrategies.size
    }

    fun addStrategy(strategy: Strategy) {
        unusedStrategies.add(strategy)
        notifyDataSetChanged()
    }

    class UnusedItemViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val addButton: Button = itemView.findViewById(R.id.add_to_used_strategies_button)
    }
}