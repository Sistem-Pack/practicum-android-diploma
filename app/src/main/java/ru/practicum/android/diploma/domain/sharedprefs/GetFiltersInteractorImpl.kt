package ru.practicum.android.diploma.domain.sharedprefs

class GetFiltersInteractorImpl(private val getFiltersRepository: GetFiltersRepository) : GetFiltersInteractor {
    override suspend fun getCountryId(): String {
        return getFiltersRepository.getCountryId()
    }

    override suspend fun getRegionId(): String {
        return getFiltersRepository.getRegionId()
    }

    override suspend fun getIndustryId(): String {
        return getFiltersRepository.getIndustryId()
    }

    override suspend fun getSalaryAmount(): Int {
        return getFiltersRepository.getSalaryAmount()
    }

    override suspend fun getDoNotShowWithoutSalarySetting(): Boolean {
        return getFiltersRepository.getDoNotShowWithoutSalarySetting()
    }
}
