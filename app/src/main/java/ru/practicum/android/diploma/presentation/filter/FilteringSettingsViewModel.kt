package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor

class FilteringSettingsViewModel(
    private val filtersInteractorImpl: FiltersInteractor
) : ViewModel() {

    private val _filter = MutableLiveData<Filters>()
    val liveData: LiveData<Filters> = _filter
    var oldFilter = Filters(
        countryId = "",
        countryName = "",
        regionId = "",
        regionName = "",
        industryId = "",
        industryName = "",
        salary = 0,
        doNotShowWithoutSalarySetting = false
    )

    fun onCreate(){
        viewModelScope.launch {
            _filter.value = filtersInteractorImpl.getFiltersFromSharedPrefs()
        }
        oldFilter = _filter.value!!
    }
}
