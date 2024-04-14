package ru.practicum.android.diploma.ui.region.model

import ru.practicum.android.diploma.domain.models.areas.AreaCountry

sealed class RegionFragmentStatus {
    class ListOfCountries(var countries: ArrayList<AreaCountry>) : RegionFragmentStatus()
    data object Bad : RegionFragmentStatus()
    data object NoConnection : RegionFragmentStatus()
    data object NoLoaded : RegionFragmentStatus()
}
