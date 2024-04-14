package ru.practicum.android.diploma.ui.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.industry.Industry

class IndustriesViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_industry, parent, false)
    ) {
    private val industryName: TextView = itemView.findViewById(R.id.tvIndustryName)
    val checkboxIndustry: CheckBox = itemView.findViewById(R.id.checkboxIndustry)

    fun bind(model: Industry, selectedIndustryId: String) {
        industryName.text = model.name
        checkboxIndustry.isChecked = model.id == selectedIndustryId
    }
}
