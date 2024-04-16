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
        return when {
            this === other -> true
            other !is Filters -> false
            countryId != other.countryId || countryName != other.countryName -> false
            regionId != other.regionId || regionName != other.regionName -> false
            industryId != other.industryId || industryName != other.industryName -> false
            salary != other.salary -> false
            doNotShowWithoutSalarySetting != other.doNotShowWithoutSalarySetting -> false
            else -> true
        }
    }
}
