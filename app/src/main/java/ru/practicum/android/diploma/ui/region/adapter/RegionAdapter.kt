package ru.practicum.android.diploma.ui.region.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.areas.AreaSubject

class RegionAdapter(
    private val regions: List<AreaSubject>
) : RecyclerView.Adapter<RegionViewHolder>() {

    var itemClickListener: ((AreaSubject) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_region, parent, false)
        return RegionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return regions.size
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        holder.bind(regions[position])
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(regions[position])
        }
    }
}
