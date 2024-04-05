package ru.practicum.android.diploma.data.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.vacancy.VacancyResponse
import ru.practicum.android.diploma.data.dto.vacancy.VacancySearchRequest
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.search.VacancyRepository
import ru.practicum.android.diploma.util.SalaryInfo

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val utils: SalaryInfo
) : VacancyRepository {
    override fun searchVacancy(params: Map<String, String>): Flow<VacancySearchResult> {
        TODO("Not yet implemented")
    }

    override fun searchVacancy(expression: String): Flow<VacancySearchResult> = flow {
        val response = networkClient.doVacancySearch(VacancySearchRequest(expression))
        when (response.resultResponse) {
            ResponseStatus.OK -> {
                val vacancies: List<Vacancy> = (response as VacancyResponse).vacancies!!.map {
                    Vacancy(
                        vacancyId = it.id,
                        vacancyName = it.name.toString(),
                        employer = it.employer?.name.toString(),
                        areaRegion = it.area?.name.toString(),
                        salary = utils.getSalaryInfo(
                            it.salary?.currency.toString(),
                            it.salary?.from.toString(),
                            it.salary?.to.toString()
                        ),
                        artworkUrl = it.employer?.logoUrls?.smallLogoUrl90.toString()
                    )
                }
                emit(
                    VacancySearchResult(
                        vacancies,
                        ResponseStatus.OK,
                        response.found ?: 0,
                        response.page ?: 0,
                        response.pages ?: 0
                    )
                )
            }

            ResponseStatus.NO_CONNECTION -> {
                emit(
                    VacancySearchResult(emptyList(), ResponseStatus.NO_CONNECTION, 0, 0, 0)
                )
            }

            ResponseStatus.BAD -> {
                emit(
                    VacancySearchResult(emptyList(), ResponseStatus.BAD, 0, 0, 0)
                )
            }

            else -> {
            }
        }
    }
}
