package ru.practicum.android.diploma.domain.industry.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.industry.IndustryRepository
import ru.practicum.android.diploma.domain.models.industry.IndustrySearchResult

class IndustryInteractorImpl(
    val repository: IndustryRepository
) : IndustryInteractor {
    override fun getIndustry(): Flow<IndustrySearchResult> {
        TODO("Not yet implemented")
    }
}
