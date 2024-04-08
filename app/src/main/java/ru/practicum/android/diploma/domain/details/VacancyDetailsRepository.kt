package ru.practicum.android.diploma.domain.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetailsResult

interface VacancyDetailsRepository {
    fun detailsVacancy(id: String): Flow<VacancyDetailsResult>
}
