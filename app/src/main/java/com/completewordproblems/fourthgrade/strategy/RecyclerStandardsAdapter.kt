package com.completewordproblems.fourthgrade.strategy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.completewordproblems.fourthgrade.R
import com.completewordproblems.fourthgrade.models.Standard
import com.google.android.material.checkbox.MaterialCheckBox

class RecyclerStandardsAdapter(
    val standards: ArrayList<Standard>,
    val onConceptClickListener: OnStandardClickListener
) :
    RecyclerView.Adapter<RecyclerStandardsAdapter.ConceptViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConceptViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.standard_list_layout, parent, false)
        val itemViewHolder = ConceptViewHolder(view)
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: ConceptViewHolder, position: Int) {
        holder.textView.text = standards[position].id
        holder.itemView.setOnClickListener {
            onConceptClickListener.onStandardClicked(standards[position])
            holder.checkBox.isChecked = true
        }
    }

    override fun getItemCount(): Int {
        return standards.size
    }

    class ConceptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: MaterialCheckBox = itemView.findViewById(R.id.is_complete_check_box)
        val textView: TextView = itemView.findViewById(R.id.label)
    }
}