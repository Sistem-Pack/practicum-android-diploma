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

        if (countryId != other.countryId || countryName != other.countryName || regionId != other.regionId) return false
        if (regionName != other.regionName || industryId != other.industryId || industryName != other.industryName) return false
        if (salary != other.salary || doNotShowWithoutSalarySetting != other.doNotShowWithoutSalarySetting) return false

        return true
    }
}
