package ru.practicum.android.diploma.data.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.vacancy.VacancyDto
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
                    checkSalaryCurrency(it)
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
                    VacancySearchResult(
                        emptyList(),
                        ResponseStatus.NO_CONNECTION,
                        0, 0, 0
                    )
                )
            }
            ResponseStatus.BAD -> {
                emit(
                    VacancySearchResult(
                        emptyList(),
                        ResponseStatus.BAD,
                        0, 0, 0
                    )
                ) }
            else -> {
            } } }

    private fun checkSalaryCurrency(vacancyDto: VacancyDto): Vacancy {
        return Vacancy(
            vacancyId = vacancyDto.id,
            vacancyName = vacancyDto.name.toString(),
            employer = vacancyDto.employer?.name.toString(),
            areaRegion = vacancyDto.area?.name.toString(),
            salary = utils.getSalaryInfo(
                if (vacancyDto.salary?.currency == null) "" else vacancyDto.salary.currency.toString(),
                if (vacancyDto.salary?.from == null) "" else vacancyDto.salary.from.toString(),
                if (vacancyDto.salary?.to == null) "" else vacancyDto.salary.toString()
            ),
            artworkUrl = vacancyDto.employer?.logoUrls?.smallLogoUrl90.toString()
        )
    }
}
