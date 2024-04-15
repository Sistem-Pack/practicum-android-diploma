package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.areas.AreasRequest
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.industry.IndustriesRequest
import ru.practicum.android.diploma.data.dto.industry.IndustriesResponse
import ru.practicum.android.diploma.data.dto.vacancy.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.vacancy.VacancySearchRequestTemp

interface NetworkClient {
    suspend fun doVacancySearch(request: VacancySearchRequestTemp): Response

    suspend fun doVacancySearch(request: VacancySearchRequest): Response

    suspend fun doVacancyDetailsSearch(request: VacancyDetailsRequest): Response

    suspend fun getIndustries(request: IndustriesRequest): IndustriesResponse

    suspend fun getAreas(request: AreasRequest): Response
}
