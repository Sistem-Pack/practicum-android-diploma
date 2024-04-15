package ru.practicum.android.diploma.domain.models

data class AreaFilters(
    val countryId: String,
    val countryName: String,
    val regionId: String,
    val regionName: String,
    val callback: Boolean = false
)
