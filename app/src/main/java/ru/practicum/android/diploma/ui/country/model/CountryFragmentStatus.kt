package ru.practicum.android.diploma.ui.country.model

import ru.practicum.android.diploma.domain.models.areas.AreaCountry

sealed class CountryFragmentStatus {
    class ListOfCountries(var countries: ArrayList<AreaCountry>) : CountryFragmentStatus()
    data object Bad : CountryFragmentStatus()
    data object NoConnection : CountryFragmentStatus()
    data object NoLoaded : CountryFragmentStatus()
}
