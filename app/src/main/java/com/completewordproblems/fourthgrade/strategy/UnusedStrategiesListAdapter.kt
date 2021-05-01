package com.completewordproblems.fourthgrade.strategy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.models.Strategy

class UnusedStrategiesListAdapter :
    RecyclerView.Adapter<UnusedStrategiesListAdapter.UnusedItemViewHolder>() {

    val unusedStrategies =
        arrayListOf<Strategy>(Strategy.DRAW_THE_SCENE, Strategy.INSPECT_KEY_WORDS)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnusedItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.unused_strategy_layout, parent, false)
        val itemViewHolder = UnusedItemViewHolder(view)
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: UnusedItemViewHolder, position: Int) {
        holder.textView.text = unusedStrategies[position].displayText
    }

    override fun getItemCount(): Int {
        return unusedStrategies.size
    }

    class UnusedItemViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val addButton: Button = itemView.findViewById(R.id.add_to_used_strategies_button)
    }
}