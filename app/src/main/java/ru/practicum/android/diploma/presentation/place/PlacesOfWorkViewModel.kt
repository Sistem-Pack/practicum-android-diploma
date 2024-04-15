package ru.practicum.android.diploma.presentation.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.AreaFilters
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.ui.place.model.PlacesOfWorkFragmentStatus

class PlacesOfWorkViewModel(
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {

    private val _placeOfWorkState = MutableLiveData<PlacesOfWorkFragmentStatus>()
    val placeOfWorkState: LiveData<PlacesOfWorkFragmentStatus> = _placeOfWorkState

    fun preloadCountryState() {
        viewModelScope.launch(Dispatchers.IO) {
            val settings = filtersInteractor.getFiltersFromSharedPrefsForAreas()
            if (settings.callback) {
                filtersInteractor.getFiltersFromSharedPrefsForAreas().apply {
                    _placeOfWorkState.postValue(
                        PlacesOfWorkFragmentStatus.SavedPlacesFilter(
                            countryName = this.countryName,
                            regionName = this.regionName
                        )
                    )
                    filtersInteractor.putFiltersInSharedPrefsForAreas(
                        AreaFilters(
                            countryId = this.countryId,
                            countryName = this.countryName,
                            regionId = this.regionId,
                            regionName = this.regionName,
                            callback = false
                        )
                    )
                }
            } else {
                filtersInteractor.getFiltersFromSharedPrefs().apply {
                    filtersInteractor.putFiltersInSharedPrefsForAreas(
                        AreaFilters(
                            countryId = this.countryId,
                            countryName = this.countryName,
                            regionId = this.regionId,
                            regionName = this.regionName
                        )
                    )
                    _placeOfWorkState.postValue(
                        PlacesOfWorkFragmentStatus.SavedPlacesFilter(
                            countryName = this.countryName,
                            regionName = this.regionName
                        )
                    )
                }
            }
        }
    }

    fun clearRegion() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefsForAreas().apply {
                filtersInteractor.putFiltersInSharedPrefsForAreas(
                    AreaFilters(
                        countryId = this.countryId,
                        countryName = this.countryName,
                        regionId = "",
                        regionName = ""
                    )
                )
            }
        }
        preloadCountryState()
    }

    fun clearCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefsForAreas().apply {
                filtersInteractor.putFiltersInSharedPrefsForAreas(
                    AreaFilters(
                        countryId = "",
                        countryName = "",
                        regionId = "",
                        regionName = ""
                    )
                )
            }
        }
        preloadCountryState()
    }

    fun clearAllSettingsForFragment() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.clearAllFiltersInSharedPrefsForAreas()
        }
    }

    fun setSaveSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefsForAreas().apply {
                filtersInteractor.getFiltersFromSharedPrefs().let {
                    filtersInteractor.putFiltersInSharedPrefs(
                        Filters(
                            countryId = this.countryId,
                            countryName = this.countryName,
                            regionId = this.regionId,
                            regionName = this.regionName,
                            industryId = it.industryId,
                            industryName = it.industryName,
                            salary = it.salary,
                            doNotShowWithoutSalarySetting = it.doNotShowWithoutSalarySetting

                        )
                    )
                }
            }
        }
    }

}
