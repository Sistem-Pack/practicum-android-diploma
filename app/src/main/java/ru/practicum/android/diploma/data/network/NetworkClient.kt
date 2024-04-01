package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {

    suspend fun doVacancySearch(request: Map<String, String>): Response

    suspend fun doVacancyDetailsSearch(id: String): Response

}
