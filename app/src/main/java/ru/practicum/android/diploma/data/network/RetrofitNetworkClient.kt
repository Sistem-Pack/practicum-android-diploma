package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.industry.IndustriesRequest
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
                    Log.d(ERROR_TAG, "$e")
                    Response().apply { resultResponse = ResponseStatus.BAD }
                }
            }
        } else {
            return Response().apply {
                resultResponse = ResponseStatus.NO_CONNECTION
            }
        }
    }

    override suspend fun doVacancyDetailsSearch(request: VacancyDetailsRequest): Response {
        if (util.isConnected()) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhApi.searchVacancyDetails(request.id)
                    response.apply {
                        resultResponse = ResponseStatus.OK
                    }
                } catch (error: Exception) {
                    Log.d(ERROR_TAG, "$error")
                    Response().apply {
                        resultResponse = ResponseStatus.BAD
                    }
                } catch (error: HttpException) {
                    Log.d(ERROR_TAG, "$error")
                    Response().apply {
                        resultResponse = ResponseStatus.BAD
                        resultCode = if (error.message.equals("HTTP 404 ")) {
                            ABSENCE_CODE
                        } else {
                            0
                        }
                    }
                }
            }
        } else {
            return Response().apply {
                resultResponse = ResponseStatus.NO_CONNECTION
            }
        }
    }

    override suspend fun getIndustries(request: IndustriesRequest): Response {
        if (util.isConnected()) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = hhApi.getIndustries(request.locale, request.host)
                    response.apply {
                        resultResponse = ResponseStatus.OK
                    }
                } catch (error: UnknownHostException) {
                    Log.d(ERROR_TAG, "$error")
                    Response().apply {
                        resultResponse = ResponseStatus.BAD
                    }
                } catch (error: HttpException) {
                    Log.d(ERROR_TAG, "$error")
                    Response().apply {
                        resultResponse = ResponseStatus.BAD
                        resultCode = if (error.message.equals("HTTP 404 ")) {
                            ABSENCE_CODE
                        } else {
                            0
                        }
                    }
                }
            }
        } else {
            return Response().apply {
                resultResponse = ResponseStatus.NO_CONNECTION
            }
        }
    }

    companion object {
        private const val ABSENCE_CODE = 404
        private const val ERROR_TAG = "RetrofitError"
    }

}
