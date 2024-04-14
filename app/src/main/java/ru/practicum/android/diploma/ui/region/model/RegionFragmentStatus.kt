package ru.practicum.android.diploma.ui.region.model

import ru.practicum.android.diploma.domain.models.areas.AreaSubject

sealed class RegionFragmentStatus {
    class ListOfRegions(var regions: ArrayList<AreaSubject>) : RegionFragmentStatus()
    data object Bad : RegionFragmentStatus()
    data object NoConnection : RegionFragmentStatus()
    data object NoLoaded : RegionFragmentStatus()
}
