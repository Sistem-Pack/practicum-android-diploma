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
//    override fun equals(other: Any?): Boolean {
//        return when {
//            this === other -> true
//            other !is Filters -> false
//            countryId != other.countryId || countryName != other.countryName -> false
//            regionId != other.regionId || regionName != other.regionName -> false
//            industryId != other.industryId || industryName != other.industryName -> false
//            salary != other.salary -> false
//            doNotShowWithoutSalarySetting != other.doNotShowWithoutSalarySetting -> false
//            else -> true
//        }
//    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Filters) return false

        if (countryId != other.countryId || countryName != other.countryName) return false
        if (regionId != other.regionId || regionName != other.regionName) return false
        if (industryId != other.industryId || industryName != other.industryName) return false
        if (salary != other.salary) return false
        if (doNotShowWithoutSalarySetting != other.doNotShowWithoutSalarySetting) return false

        return true
    }
}
