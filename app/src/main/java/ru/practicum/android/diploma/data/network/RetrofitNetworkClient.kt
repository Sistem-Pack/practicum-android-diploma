package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.vacancy.VacancySearchRequest
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.util.Utilities
import java.net.UnknownHostException

class RetrofitNetworkClient(
    private val hhApi: HHApi,
    private val util: Utilities
) : NetworkClient {

    override suspend fun doVacancySearch(request: Map<String, String>): Response {
        TODO("Not yet implemented")
    }

    override suspend fun doVacancySearch(request: VacancySearchRequest): Response {
        if (util.isConnected()) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhApi.searchVacancies(request.expression, request.page, request.perPage)
                    response.apply {
                        resultResponse = ResponseStatus.OK
                    }
                } catch (e: UnknownHostException) {
                    Log.d("Exception", "$e")
                    Response().apply { resultResponse = ResponseStatus.BAD }
                }
            }
        } else {
            return Response().apply {
                resultResponse = ResponseStatus.NO_CONNECTION
            }
        }
    }

    override suspend fun doVacancyDetailsSearch(id: String): Response {
        if (util.isConnected()) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhApi.searchVacancyDetails(id)
                    response.apply {
                        resultResponse = ResponseStatus.OK
                    }
                } catch (error: UnknownHostException) {
                    Log.d("Exception", "$error")
                    Response().apply {
                        resultResponse = ResponseStatus.BAD
                    }
                } catch (error: HttpException) {
                    Log.d("Exception", "$error")
                    Response().apply {
                        resultResponse = ResponseStatus.BAD
                        resultCode = if (error.message.equals("HTTP 404 ")) 404 else 0
                    }
                }
            }
        } else {
            return Response().apply {
                resultResponse = ResponseStatus.NO_CONNECTION
            }
        }
    }

}
