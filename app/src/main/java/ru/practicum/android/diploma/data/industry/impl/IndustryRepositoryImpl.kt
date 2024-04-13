package ru.practicum.android.diploma.data.industry.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.industry.IndustriesRequest
import ru.practicum.android.diploma.data.dto.industry.IndustriesResponse
import ru.practicum.android.diploma.data.dto.industry.IndustryDto
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.industry.IndustryRepository
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.industry.Industry
import ru.practicum.android.diploma.domain.models.industry.IndustrySearchResult

class IndustryRepositoryImpl(
    val networkClient: NetworkClient,
    val request: IndustriesRequest
) : IndustryRepository {
    override fun getIndustry(): Flow<IndustrySearchResult> = flow {
        val response = networkClient.getIndustries(request)
        when (response.resultResponse) {
            ResponseStatus.OK -> {
                val industry: List<Industry>? = (response as IndustriesResponse).industries?.let {
                    formatToIndustry(it)
                }
                emit(
                    IndustrySearchResult(
                        industry!!,
                        ResponseStatus.OK,
                    )
                )
            }
            ResponseStatus.NO_CONNECTION -> {
                emit(
                    IndustrySearchResult(
                        emptyList(),
                        ResponseStatus.NO_CONNECTION
                    )
                )

            }
            else -> {
                emit(
                    IndustrySearchResult(
                        emptyList(),
                        ResponseStatus.BAD
                    )
                )
            }
        }
    }

    private fun formatToIndustry(dto: List<IndustryDto>): List<Industry> {
        val resultListOfIndustries = mutableListOf<Industry>()
        for (industry in dto) {
            resultListOfIndustries.add(
                Industry(
                    industry.id,
                    industry.name
                )
            )
            if (!industry.industries.isNullOrEmpty()) {
                for (nestedIndustry in industry.industries) {
                    resultListOfIndustries.add(
                        Industry(
                            nestedIndustry.id,
                            nestedIndustry.name
                        )
                    )
                }
            } else {
                continue
            }
        }
        return resultListOfIndustries
    }
}
