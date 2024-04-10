package ru.practicum.android.diploma.ui.favorites

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy

class VacancyAdapter(private val clickListener: VacancyClickListener) :
    RecyclerView.Adapter<VacancyViewHolder>() {

    var vacancies = ArrayList<Vacancy>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder =
        VacancyViewHolder(parent)

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener { clickListener.onVacancyClick(vacancies[position]) }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }

}
