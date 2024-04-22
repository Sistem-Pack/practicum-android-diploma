package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.VacancySearchResult

interface VacancyInteractor {
    fun searchVacancy(expression: String, filters: Filters, page: Int): Flow<VacancySearchResult>
}
