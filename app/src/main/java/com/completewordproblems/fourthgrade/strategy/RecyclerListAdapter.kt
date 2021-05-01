package com.completewordproblems.fourthgrade.strategy

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.models.Strategy

class RecyclerListAdapter(val dragStartListener: OnDragStartListener) :
    RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>(), ItemTouchHelperAdapter {

    val strategies = arrayListOf<Strategy>(
        Strategy.READ_THE_PROBLEM, Strategy.INSPECT_KEY_WORDS, Strategy.WHAT_ARE_YOU_LOOKING_FOR,
        Strategy.WHAT_INFORMATION_IS_NEEDED, Strategy.DRAW_THE_SCENE, Strategy.WRITE_THE_EQUATION, Strategy.SOLVE_THE_PROBLEM
    )
    val stragiesToUse = arrayListOf<Strategy>(
        Strategy.READ_THE_PROBLEM, Strategy.INSPECT_KEY_WORDS, Strategy.WHAT_ARE_YOU_LOOKING_FOR,
        Strategy.WHAT_INFORMATION_IS_NEEDED, Strategy.DRAW_THE_SCENE, Strategy.WRITE_THE_EQUATION, Strategy.SOLVE_THE_PROBLEM
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.used_strategy_layout, parent, false)
        val itemViewHolder = ItemViewHolder(view)
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = stragiesToUse[position].displayText
        holder.handleView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action ?: -1 == MotionEvent.ACTION_DOWN) {
                    this@RecyclerListAdapter.dragStartListener.onDragStarted(holder)
                }
                return false
            }

        })
    }

    override fun getItemCount(): Int {
        return stragiesToUse.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val prev: Strategy = stragiesToUse.removeAt(fromPosition)
        stragiesToUse.add(if (toPosition > fromPosition) (toPosition - 1) else (toPosition), prev)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        val dismissed = stragiesToUse.removeAt(position)
        dragStartListener.onItemDismissed(dismissed)
        notifyItemRemoved(position)
    }

    fun addStrategy(strategy: Strategy) {
        stragiesToUse.add(strategy)
    }

    class ItemViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView),
        ItemTouchHelperViewHolder {

        val textView: TextView = itemView.findViewById(R.id.text)
        val handleView: ImageView = itemView.findViewById(R.id.handle)
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

    }
}