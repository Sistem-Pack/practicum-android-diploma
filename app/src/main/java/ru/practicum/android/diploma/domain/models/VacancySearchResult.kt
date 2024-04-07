package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.domain.models.vacancy.Vacancy

data class VacancySearchResult(
    val results: List<Vacancy>,
    val responseStatus: ResponseStatus,
    val found: Int,
    val page: Int,
    val pages: Int
)
