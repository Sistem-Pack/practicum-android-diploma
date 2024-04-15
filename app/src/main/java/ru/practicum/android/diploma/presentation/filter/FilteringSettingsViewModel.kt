package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.presentation.main.MainViewModel

class FilteringSettingsViewModel(
    private val filtersInteractorImpl: FiltersInteractor
) : ViewModel() {

    private val _filter = MutableLiveData(EMPTY_FILTER)
    val liveData: LiveData<Filters> = _filter
    private var filterDataIsChange = false
    var oldFilter = (viewModel as? MainViewModel)?.getCurrentFilter()

    fun onCreate(){
        var savedFilter: Filters = EMPTY_FILTER
        viewModelScope.launch {
            savedFilter = filtersInteractorImpl.getFiltersFromSharedPrefs()
        }
    }



    companion object {
        private val EMPTY_FILTER = Filters(
            countryId = "",
            countryName = "",
            regionId = "",
            regionName = "",
            industryId = "",
            industryName = "",
            salary = 0,
            doNotShowWithoutSalarySetting = false
        )
    }
}
