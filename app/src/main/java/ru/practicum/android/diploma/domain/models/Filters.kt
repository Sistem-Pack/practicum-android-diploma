package ru.practicum.android.diploma.domain.models

data class Filters(
    val countryId: String,
    val countryName: String,
    val regionId: String,
    val regionName: String,
    val industryId: String,
    val industryName: String,
    val salary: Int,
    val doNotShowWithoutSalarySetting: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Filters) return false

        if (compareHalfOfFilters(this, other)) return false
        if (salary != other.salary) return false
        if (doNotShowWithoutSalarySetting != other.doNotShowWithoutSalarySetting) return false

        return true
    }

    private fun compareHalfOfFilters(firstFilter: Filters, secondFilter: Filters): Boolean {
        return firstFilter.countryId != secondFilter.countryId
            || firstFilter.countryName != secondFilter.countryName
            || firstFilter.regionId != secondFilter.regionId
            || firstFilter.regionName != secondFilter.regionName
            || firstFilter.industryId != secondFilter.industryId
            || firstFilter.industryName != secondFilter.industryName
    }
}
