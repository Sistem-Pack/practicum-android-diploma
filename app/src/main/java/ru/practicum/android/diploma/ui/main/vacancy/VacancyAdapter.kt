package ru.practicum.android.diploma.ui.main.vacancy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy

class VacancyAdapter(
    private val vacancies: List<Vacancy>
) : RecyclerView.Adapter<VacancyViewHolder>() {

    var itemClickListener: ((Vacancy) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(vacancies[position])
        }
    }
}
