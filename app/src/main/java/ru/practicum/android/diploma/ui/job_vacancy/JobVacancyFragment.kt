package ru.practicum.android.diploma.ui.job_vacancy

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.job_vacancy.JobVacancyViewModel

class JobVacancyFragment(): Fragment() {

    private val viewModel by viewModel<JobVacancyViewModel>()
}
