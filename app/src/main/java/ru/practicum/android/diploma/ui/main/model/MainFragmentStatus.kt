package ru.practicum.android.diploma.ui.main.model

import ru.practicum.android.diploma.domain.models.vacancy.Vacancy

sealed class MainFragmentStatus {
    class ListOfVacancies(var vacancies: List<Vacancy>) : MainFragmentStatus()
    data object Bad : MainFragmentStatus()
    data object NoConnection : MainFragmentStatus()
    data object Loading : MainFragmentStatus()
    data object Default : MainFragmentStatus()
}
