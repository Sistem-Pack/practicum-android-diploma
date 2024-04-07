package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails

data class VacancyDetailsResult(
    val results: VacancyDetails?,
    val responseStatus: ResponseStatus,
)
