package com.completewordproblems.fourthgrade.strategy

import androidx.recyclerview.widget.RecyclerView

interface OnDragStartListener {
    fun onDragStarted(viewHolder: RecyclerView.ViewHolder)
}