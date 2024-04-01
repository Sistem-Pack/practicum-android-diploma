package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsResponse
import ru.practicum.android.diploma.data.dto.vacancy.VacancyResponse

interface HHApi {

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: DiplomPracticumWithHH"
    )
    @GET("/vacancies/")
    suspend fun searchVacancies(
        @QueryMap params: Map<String, String>
    ): VacancyResponse

    @GET("/vacancies/")
    suspend fun searchVacancies(
        @Query("text") query: String
    ): VacancyResponse

    @GET("/vacancies/{vacancy_id}")
    suspend fun searchVacancyDetails(@Path("vacancy_id") id: String): VacancyDetailsResponse

}
