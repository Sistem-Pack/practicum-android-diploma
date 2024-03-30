package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig

interface HHApi {

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: DiplomPracticumWithHH"
    )
    @GET("/vacancies/")
    suspend fun searchVacancies(
        @QueryMap params: Map<String, String>
    )

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: DiplomPracticumWithHH"
    )
    @GET("/vacancies/")
    suspend fun searchVacancies(
        @Query("text") query: String
    )

}
