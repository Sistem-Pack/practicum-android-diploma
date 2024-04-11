package ru.practicum.android.diploma.domain.sharedprefs

interface FiltersRepository {
    suspend fun getCountryId(): String

    suspend fun getRegionId(): String

    suspend fun getIndustryId(): String

    suspend fun getSalaryAmount(): Int

    suspend fun getDoNotShowWithoutSalarySetting(): Boolean

    fun putCountryId(countryId: String)

    fun putRegionId(regionId: String)

    fun putIndustryId(industryId: String)

    fun putSalaryAmount(salary: Int)

    fun putDoNotShowWithoutSalarySetting(doNotShowWithoutSalarySetting: Boolean)

    fun clearAllSettings()
}
