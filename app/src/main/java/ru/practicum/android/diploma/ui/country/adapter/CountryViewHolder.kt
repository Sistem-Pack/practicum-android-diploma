package ru.practicum.android.diploma.ui.country.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.areas.AreaCountry

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val countryName: TextView = itemView.findViewById(R.id.tvCountry)

    fun bind(model: AreaCountry) {
        countryName.text = model.name
    }
}
