package ru.practicum.android.diploma.ui.region.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.areas.AreaSubject

class RegionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val regionName: TextView = itemView.findViewById(R.id.tvRegion)

    fun bind(model: AreaSubject) {
        regionName.text = model.name
    }
}
