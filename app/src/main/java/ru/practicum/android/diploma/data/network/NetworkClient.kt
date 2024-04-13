package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.areas.AreasRequest
import ru.practicum.android.diploma.data.dto.areas.AreasResponse
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.industry.IndustriesRequest
import ru.practicum.android.diploma.data.dto.vacancy.VacancySearchRequest

interface NetworkClient {
    suspend fun doVacancySearch(request: Map<String, String>): Response

    suspend fun doVacancySearch(request: VacancySearchRequest): Response

    suspend fun doVacancyDetailsSearch(request: VacancyDetailsRequest): Response

    suspend fun getIndustries(request: IndustriesRequest): Response

    suspend fun getAreas(request: AreasRequest): AreasResponse
}
