package ru.practicum.android.diploma.ui.industry

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.industry.Industry

class IndustriesAdapter(private val clickListener: IndustryClickListener) :
    RecyclerView.Adapter<IndustriesViewHolder>() {

    var industries = ArrayList<Industry>()
    var selectedIndustryId = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder =
        IndustriesViewHolder(parent)

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        holder.bind(industries[position], selectedIndustryId)
        holder.checkboxIndustry.setOnClickListener { clickListener.onIndustryClick(industries[position]) }
        //holder.itemView.setOnClickListener { clickListener.onIndustryClick(industries[position]) }
    }

    override fun getItemCount(): Int {
        return industries.size
    }

    fun interface IndustryClickListener {
        fun onIndustryClick(industry: Industry)
    }

}
