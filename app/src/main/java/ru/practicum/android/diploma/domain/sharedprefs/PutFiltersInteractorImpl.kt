package ru.practicum.android.diploma.domain.sharedprefs

class PutFiltersInteractorImpl(private val putFiltersRepository: PutFiltersRepository) : PutFiltersInteractor {
    override fun putCountryId(countryId: String) {
        return putFiltersRepository.putCountryId(countryId)
    }

    override fun putRegionId(regionId: String) {
        return putFiltersRepository.putRegionId(regionId)
    }

    override fun putIndustryId(industryId: String) {
        return putFiltersRepository.putIndustryId(industryId)
    }

    override fun putSalaryAmount(salary: Int) {
        return putFiltersRepository.putSalaryAmount(salary)
    }

    override fun putDoNotShowWithoutSalarySetting(doNotShowWithoutSalarySetting: Boolean) {
        return putFiltersRepository.putDoNotShowWithoutSalarySetting(doNotShowWithoutSalarySetting)
    }

    override fun clearAllSettings() {
        return putFiltersRepository.clearAllSettings()
    }
}
