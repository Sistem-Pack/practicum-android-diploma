package ru.practicum.android.diploma.domain.details.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetailsResult

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyDetailsInteractor {

    override fun vacancyDetails(id: String): Flow<VacancyDetailsResult> {
        return repository.detailsVacancy(id)
    }
}
