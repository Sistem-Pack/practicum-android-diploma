package ru.practicum.android.diploma.domain.sharedprefs

class FiltersInteractorImpl(private val filtersRepository: FiltersRepository) : FiltersInteractor {
    override suspend fun getCountryId(): String {
        return filtersRepository.getCountryId()
    }

    override suspend fun getRegionId(): String {
        return filtersRepository.getRegionId()
    }

    override suspend fun getSalaryAmount(): Int {
        return filtersRepository.getSalaryAmount()
    }

    override suspend fun getDoNotShowWithoutSalarySetting(): Boolean {
        return filtersRepository.getDoNotShowWithoutSalarySetting()
    }

    override fun putCountryId(countryId: String) {
        return filtersRepository.putCountryId(countryId)
    }

    override fun putRegionId(regionId: String) {
        return filtersRepository.putRegionId(regionId)
    }

    override fun putSalaryAmount(salary: Int) {
        return filtersRepository.putSalaryAmount(salary)
    }

    override fun putDoNotShowWithoutSalarySetting(doNotShowWithoutSalarySetting: Boolean) {
        return filtersRepository.putDoNotShowWithoutSalarySetting(doNotShowWithoutSalarySetting)
    }

    override fun clearAllSettings() {
        return filtersRepository.clearAllSettings()
    }
}
