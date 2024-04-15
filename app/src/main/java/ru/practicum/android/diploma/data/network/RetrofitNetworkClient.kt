package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.areas.AreasRequest
import ru.practicum.android.diploma.data.dto.areas.AreasResponse
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.industry.IndustriesRequest
import ru.practicum.android.diploma.data.dto.industry.IndustriesResponse
import ru.practicum.android.diploma.data.dto.vacancy.VacancySearchRequest
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.util.Utilities
import java.io.IOException
import java.net.SocketTimeoutException
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
                } catch (error: UnknownHostException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    Response().apply { resultResponse = ResponseStatus.BAD }
                } catch (error: HttpException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    Response().apply { resultResponse = ResponseStatus.BAD }
                } catch (error: SocketTimeoutException) {
                    Log.d(ERROR_TAG, error.message.toString())
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
                    response.apply { resultResponse = ResponseStatus.OK }
                } catch (error: UnknownHostException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    Response().apply { resultResponse = ResponseStatus.BAD }
                } catch (error: HttpException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    Response().apply {
                        resultResponse = ResponseStatus.BAD
                        resultCode = if (error.message.equals("HTTP 404 ")) {
                            ABSENCE_CODE
                        } else {
                            0
                        }
                    }
                } catch (error: SocketTimeoutException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    Response().apply { resultResponse = ResponseStatus.BAD }
                } catch (error: IOException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    Response().apply { resultResponse = ResponseStatus.BAD }
                }
            }
        } else {
            return Response().apply { resultResponse = ResponseStatus.NO_CONNECTION }
        }
    }

    override suspend fun getIndustries(request: IndustriesRequest): IndustriesResponse {
        return if (!util.isConnected()) {
            IndustriesResponse(
                emptyList(),
                resultResponseStatus = ResponseStatus.NO_CONNECTION
            )
        } else {
            withContext(Dispatchers.IO) {
                try {
                    val response = hhApi.getIndustries(request.locale, request.host)
                    IndustriesResponse(
                        response.toList(),
                        resultResponseStatus = ResponseStatus.OK
                    )
                } catch (error: UnknownHostException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    IndustriesResponse(
                        emptyList(),
                        resultResponseStatus = ResponseStatus.BAD
                    )
                } catch (error: HttpException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    IndustriesResponse(
                        emptyList(),
                        resultResponseStatus = ResponseStatus.NO_CONNECTION,
                        resultCode = if (error.message.equals("HTTP 404 ")) {
                            ABSENCE_CODE
                        } else {
                            0
                        }
                    )
                } catch (error: SocketTimeoutException) {
                    Log.d(ERROR_TAG, error.message.toString())
                    IndustriesResponse(
                        emptyList(),
                        resultResponseStatus = ResponseStatus.BAD
                    )
                }
            }
        }
    }

    override suspend fun getAreas(request: AreasRequest): AreasResponse {
        return if (!util.isConnected()) {
            AreasResponse(
                emptyList(),
                resultResponseStatus = ResponseStatus.NO_CONNECTION
            )
        } else {
            getAreasEx(request)
        }
    }

    private suspend fun getAreasEx(request: AreasRequest): AreasResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = hhApi.getAreas(request.locale, request.host)
                AreasResponse(
                    response.toList(),
                    resultResponseStatus = ResponseStatus.OK
                )
            } catch (error: UnknownHostException) {
                Log.d(ERROR_TAG, error.message.toString())
                AreasResponse(
                    emptyList(),
                    resultResponseStatus = ResponseStatus.BAD
                )
            } catch (error: HttpException) {
                Log.d(ERROR_TAG, error.message.toString())
                AreasResponse(
                    emptyList(),
                    resultResponseStatus = ResponseStatus.NO_CONNECTION,
                    resultCode = if (error.message.equals("HTTP 404 ")) {
                        ABSENCE_CODE
                    } else {
                        0
                    }
                )
            } catch (error: SocketTimeoutException) {
                Log.d(ERROR_TAG, error.message.toString())
                AreasResponse(
                    emptyList(),
                    resultResponseStatus = ResponseStatus.BAD
                )
            }
        }
    }

    companion object {
        private const val ABSENCE_CODE = 404
        private const val ERROR_TAG = "RetrofitError"
    }
}
