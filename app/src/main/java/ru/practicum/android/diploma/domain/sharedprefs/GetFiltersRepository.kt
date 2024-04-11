package ru.practicum.android.diploma.domain.sharedprefs

interface GetFiltersRepository {
    suspend fun getCountryId(): String

    suspend fun getRegionId(): String

    suspend fun getIndustryId(): String

    suspend fun getSalaryAmount(): Int

    suspend fun getDoNotShowWithoutSalarySetting(): Boolean
}
