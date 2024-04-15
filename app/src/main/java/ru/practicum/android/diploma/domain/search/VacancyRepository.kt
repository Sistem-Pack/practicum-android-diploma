package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.VacancySearchResult

interface VacancyRepository {
    fun searchVacancy(expression: String, filters: Filters, page: Int): Flow<VacancySearchResult>

    fun searchVacancy(expression: String, page: Int): Flow<VacancySearchResult>
}
