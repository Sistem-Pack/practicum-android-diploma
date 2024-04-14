package ru.practicum.android.diploma.presentation.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.areas.AreaCountry
import ru.practicum.android.diploma.domain.models.areas.AreaSubject
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.ui.region.model.RegionFragmentStatus
import ru.practicum.android.diploma.util.Utilities

class RegionSelectionViewModel(
    private val regionInteractor: AreasInteractor,
    private val filtersInteractor: FiltersInteractor,
    private val utilities: Utilities
) : ViewModel() {
    private var requestText = ""
    private var job: Job? = null
    private val regions: ArrayList<AreaSubject> = ArrayList()
    private val parentRegions: ArrayList<AreaCountry> = ArrayList()

    private val _regionStateData = MutableLiveData<RegionFragmentStatus>()
    val regionStateData: LiveData<RegionFragmentStatus> = _regionStateData

    fun onDestroy() {
        job?.cancel()
    }

    fun changeRequestText(text: String) {
        requestText = text
    }

    fun getRequestText(): String {
        return requestText
    }

    fun searchDebounce() {
        job?.cancel()
        job = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            search()
        }
    }

    fun getAllRegions() {
        if (regions.isEmpty()) {
            _regionStateData.postValue(RegionFragmentStatus.NoConnection)
        } else {
            _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(regions))
        }
    }

    fun search() {
        if (regions.isEmpty()) {
            _regionStateData.postValue(RegionFragmentStatus.Bad)
        } else {
            val filteredRegions: ArrayList<AreaSubject> = ArrayList()
            filteredRegions.addAll(regions.filter { it.name.matches(Regex(".*$requestText.*")) })
            if (filteredRegions.isEmpty()) {
                _regionStateData.postValue(RegionFragmentStatus.NoLoaded)
            } else {
                _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(filteredRegions))
            }
        }
    }

    fun getRegions() {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.getAreas().collect { result ->
                when (result.responseStatus) {
                    ResponseStatus.OK -> {
                        val idParentRegionFromSavedData = filtersInteractor.getFiltersFromSharedPrefs().countryId
                        if (idParentRegionFromSavedData.isNotEmpty()) {
                            regions.addAll(result.listSubject.filter { it.parentId == idParentRegionFromSavedData })
                            _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(regions))
                        } else {
                            regions.addAll(result.listSubject)
                            parentRegions.addAll(result.listCountry)
                            _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(regions))
                        }
                    }

                    ResponseStatus.BAD -> {
                        _regionStateData.postValue(RegionFragmentStatus.Bad)
                    }

                    ResponseStatus.NO_CONNECTION -> {
                        _regionStateData.postValue(RegionFragmentStatus.NoConnection)
                    }

                    ResponseStatus.LOADING, ResponseStatus.DEFAULT -> Unit
                }
            }
        }
    }

    fun setFilters(selectedRegionItem: AreaSubject) {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefs().apply {
                val parentCountry: AreaCountry
                val findParentInCountry = parentRegions.find { it.id == selectedRegionItem.parentId }
                val findParentInRegions = regions.find { it.id == selectedRegionItem.parentId }
                parentCountry = if (findParentInCountry == null) {
                    AreaCountry(id = findParentInRegions?.id!!, name = findParentInRegions.name)
                } else {
                    AreaCountry(id = findParentInCountry.id, name = findParentInCountry.name)
                }
                filtersInteractor.putFiltersInSharedPrefs(
                    Filters(
                        countryId = parentCountry.id,
                        countryName = parentCountry.name,
                        regionId = selectedRegionItem.id,
                        regionName = selectedRegionItem.name,
                        industryId = this.industryId,
                        industryName = this.industryName,
                        salary = this.salary,
                        doNotShowWithoutSalarySetting = this.doNotShowWithoutSalarySetting
                    )
                )
            }
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val ERROR_TAG = "ErrorLoadingProcess"
    }
}
