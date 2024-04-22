package ru.practicum.android.diploma.domain.industry

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.industry.IndustrySearchResult

interface IndustryRepository {
    fun getIndustry(): Flow<IndustrySearchResult>
}
