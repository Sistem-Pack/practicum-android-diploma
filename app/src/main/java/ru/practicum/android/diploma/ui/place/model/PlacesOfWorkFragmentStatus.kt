package ru.practicum.android.diploma.ui.place.model

sealed class PlacesOfWorkFragmentStatus {
    class SavedPlacesFilter(var countryName: String, var regionName: String) : PlacesOfWorkFragmentStatus()
}
