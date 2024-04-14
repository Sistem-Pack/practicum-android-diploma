package ru.practicum.android.diploma.presentation.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

}
