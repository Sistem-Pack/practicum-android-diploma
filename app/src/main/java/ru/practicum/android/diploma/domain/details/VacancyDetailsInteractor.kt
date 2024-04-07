package ru.practicum.android.diploma.domain.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetailsResult

interface VacancyDetailsInteractor {
    fun vacancyDetails(id: String): Flow<VacancyDetailsResult>
}
