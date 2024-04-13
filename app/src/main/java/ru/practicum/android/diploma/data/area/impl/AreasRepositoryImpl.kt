package ru.practicum.android.diploma.data.area.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.areas.AreasDto
import ru.practicum.android.diploma.data.dto.areas.AreasRequest
import ru.practicum.android.diploma.data.dto.areas.AreasResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.areas.impl.AreasRepository
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.areas.AreaCountry
import ru.practicum.android.diploma.domain.models.areas.AreaSubject
import ru.practicum.android.diploma.domain.models.areas.AreasSearchResult

class AreasRepositoryImpl(
    val networkClient: NetworkClient,
    val request: AreasRequest
) : AreasRepository {
    override fun getAreas(): Flow<AreasSearchResult> = flow {
        val response = networkClient.getAreas(request)
        when (response.resultResponse) {
            ResponseStatus.OK -> {
                val countyList: List<AreaCountry> = (response as AreasResponse).areas.let {
                    formatToAreasCounty(it)
                }

                val subjectList: List<AreaSubject> = response.areas.let {
                    formatToAreasSubject(it)
                }
                emit(
                    AreasSearchResult(
                        countyList,
                        subjectList,
                        ResponseStatus.OK
                    )
                )
            }
            ResponseStatus.NO_CONNECTION -> {
                emit(
                    AreasSearchResult(
                        emptyList(),
                        emptyList(),
                        ResponseStatus.NO_CONNECTION
                    )
                )
            }

            else -> {
                emit(
                    AreasSearchResult(
                        emptyList(),
                        emptyList(),
                        ResponseStatus.BAD
                    )
                )
            }
        }
    }

    fun formatToAreasCounty(dto: List<AreasDto>): List<AreaCountry> {
        val resultListOfAreasCountry = mutableListOf<AreaCountry>()
        for (country in dto) {
            if (country.parentId.isNullOrEmpty()) {
                resultListOfAreasCountry.add(
                    AreaCountry(
                        country.id,
                        country.name
                    )
                )
            }
        }
        return resultListOfAreasCountry
    }

    fun formatToAreasSubject(dto: List<AreasDto>): List<AreaSubject> {
        val resultListOfAreasSubject = mutableListOf<AreaSubject>()
        for (country in dto) {
            country.areas!!.forEach { subjectRegion ->
                resultListOfAreasSubject.add(
                    AreaSubject(
                        subjectRegion.id,
                        subjectRegion.parentId ?: "",
                        subjectRegion.name
                    )
                )
                if (!subjectRegion.areas.isNullOrEmpty()) {
                    subjectRegion.areas.forEach { subjectCity ->
                        resultListOfAreasSubject.add(
                            AreaSubject(
                                subjectCity.id,
                                subjectCity.parentId ?: "",
                                subjectCity.name
                            )
                        )
                    }
                }
            }
        }
        return resultListOfAreasSubject
    }
}
