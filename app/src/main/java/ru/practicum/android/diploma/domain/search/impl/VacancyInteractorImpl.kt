package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.domain.search.VacancyInteractor
import ru.practicum.android.diploma.domain.search.VacancyRepository

class VacancyInteractorImpl(
    private val repository: VacancyRepository
) : VacancyInteractor {
    override fun searchVacancy(expression: String, filters: Filters, page: Int): Flow<VacancySearchResult> {
        return repository.searchVacancy(expression, filters, page)
    }

    override fun searchVacancy(expression: String, page: Int): Flow<VacancySearchResult> {
        return repository.searchVacancy(expression = expression, page = page)
    }
}
