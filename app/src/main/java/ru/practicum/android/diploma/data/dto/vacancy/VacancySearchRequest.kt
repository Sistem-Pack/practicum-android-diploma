package ru.practicum.android.diploma.data.dto.vacancy

data class VacancySearchRequest(
    val expression: String,
    val page: Int,
    val perPage: Int = 20
)
