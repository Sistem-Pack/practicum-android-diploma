package ru.practicum.android.diploma.domain.sharedprefs

interface PutFiltersInteractor {
    fun putCountryId(countryId: String)

    fun putRegionId(regionId: String)

    fun putIndustryId(industryId: String)

    fun putSalaryAmount(salary: Int)

    fun putDoNotShowWithoutSalarySetting(doNotShowWithoutSalarySetting: Boolean)

    fun clearAllSettings()
}
