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

class RecyclerListAdapter(val dragStartListener: OnDragStartListener) :
    RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>(), ItemTouchHelperAdapter {

    val items = arrayListOf<String>("one", "two", "three", "four")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        val itemViewHolder = ItemViewHolder(view)
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = items[position]
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
        return items.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val prev: String = items.removeAt(fromPosition)
        items.add(if (toPosition > fromPosition) (toPosition - 1) else (toPosition), prev)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
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