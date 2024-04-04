package ru.practicum.android.diploma.ui.main.vacancy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.util.SalaryInfo

class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val logo: ImageView = itemView.findViewById(R.id.ivLogo)
    private val vacancyName: TextView = itemView.findViewById(R.id.tvVacancyName)
    private val vacancyEmployer: TextView = itemView.findViewById(R.id.tvEmployer)
    private val vacancySalary: TextView = itemView.findViewById(R.id.tvVacancySalary)

    fun bind(model: Vacancy) {
        vacancyName.text = model.vacancyName + itemView.context.getString(R.string.seporator) + model.areaRegion
        vacancyEmployer.text = model.employer
        vacancySalary.text = SalaryInfo(itemView.context).getSalaryInfo(
            salaryCurrency = model.salaryCurrency,
            salaryFrom = model.salaryFrom,
            salaryTo = model.salaryTo
        )

        Glide.with(itemView)
            .load(model.artworkUrl)
            .placeholder(R.drawable.logo_plug)
            .centerCrop()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.half_space)))
            .into(logo)
    }
}
