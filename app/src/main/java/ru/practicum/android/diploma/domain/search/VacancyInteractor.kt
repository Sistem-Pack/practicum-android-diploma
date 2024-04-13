package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancySearchResult

interface VacancyInteractor {
    fun searchVacancy(params: Map<String, String>): Flow<VacancySearchResult>

    fun searchVacancy(expression: String, page: Int): Flow<VacancySearchResult>
}
