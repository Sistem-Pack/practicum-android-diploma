package ru.practicum.android.diploma.presentation.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            filtersInteractor.getFiltersFromSharedPrefs().apply {
                _placeOfWorkState.postValue(
                    PlacesOfWorkFragmentStatus.SavedPlacesFilter(
                        countryName = this.countryName,
                        regionName = this.regionName
                    )
                )
            }
        }
    }

    fun clearCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefs().apply {
                filtersInteractor.putFiltersInSharedPrefs(
                    Filters(
                        countryId = "",
                        countryName = "",
                        regionId = "",
                        regionName = "",
                        industryId = this.industryId,
                        industryName = this.industryName,
                        salary = this.salary,
                        doNotShowWithoutSalarySetting = this.doNotShowWithoutSalarySetting
                    )
                )
            }
        }
        preloadCountryState()
    }

    fun clearRegion() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefs().apply {
                filtersInteractor.putFiltersInSharedPrefs(
                    Filters(
                        countryId = this.countryId,
                        countryName = this.regionId,
                        regionId = "",
                        regionName = "",
                        industryId = this.industryId,
                        industryName = this.industryName,
                        salary = this.salary,
                        doNotShowWithoutSalarySetting = this.doNotShowWithoutSalarySetting
                    )
                )
            }
        }
        preloadCountryState()
    }

}
