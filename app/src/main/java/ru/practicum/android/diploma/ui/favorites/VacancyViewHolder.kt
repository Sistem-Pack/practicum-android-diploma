package ru.practicum.android.diploma.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy

class VacancyViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vacancy, parent, false)
    ) {
    private val logo: ImageView = itemView.findViewById(R.id.ivLogo)
    private val vacancyName: TextView = itemView.findViewById(R.id.tvVacancyName)
    private val vacancyEmployer: TextView = itemView.findViewById(R.id.tvEmployer)
    private val vacancySalary: TextView = itemView.findViewById(R.id.tvVacancySalary)

    fun bind(model: Vacancy) {
        vacancyName.text = model.vacancyName + ", " + model.areaRegion
        vacancyEmployer.text = model.employer
        vacancySalary.text = model.salary

        Glide.with(itemView)
            .load(model.artworkUrl)
            .placeholder(R.drawable.logo_plug)
            .centerCrop()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.three_space)))
            .into(logo)
    }
}
