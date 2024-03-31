package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response

class RetrofitNetworkClient(
    private val hhApi: HHApi
) : NetworkClient {

    override suspend fun doVacancySearch(request: Map<String, String>): Response {
        TODO("Not yet implemented")
    }

    override suspend fun doVacancyDetailsSearch(id: String): Response {
        TODO("Not yet implemented")
    }

}
