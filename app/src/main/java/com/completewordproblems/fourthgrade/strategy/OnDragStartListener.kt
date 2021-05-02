package com.completewordproblems.fourthgrade.strategy

import androidx.recyclerview.widget.RecyclerView
import com.completewordproblems.fourthgrade.models.Strategy

interface OnDragStartListener {
    fun onDragStarted(viewHolder: RecyclerView.ViewHolder)
    fun onItemDismissed(strategy: Strategy)
}