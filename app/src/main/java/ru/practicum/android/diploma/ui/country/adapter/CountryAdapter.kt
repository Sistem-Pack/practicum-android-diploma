package ru.practicum.android.diploma.ui.country.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.areas.AreaCountry

class CountryAdapter(
    private val areas: List<AreaCountry>
) : RecyclerView.Adapter<CountryViewHolder>() {

    var itemClickListener: ((AreaCountry) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return areas.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(areas[position])
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(areas[position])
        }
    }
}
