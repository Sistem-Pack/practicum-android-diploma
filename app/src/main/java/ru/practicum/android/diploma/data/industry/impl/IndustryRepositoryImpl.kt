package ru.practicum.android.diploma.data.industry.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.industry.IndustryRepository
import ru.practicum.android.diploma.domain.models.industry.IndustrySearchResult

class IndustryRepositoryImpl(
    val networkClient: NetworkClient
) : IndustryRepository {
    override fun getIndustry(): Flow<IndustrySearchResult> {
        TODO("Not yet implemented")
    }
}
