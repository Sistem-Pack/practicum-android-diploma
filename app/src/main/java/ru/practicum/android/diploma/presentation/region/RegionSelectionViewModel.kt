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
import ru.practicum.android.diploma.domain.models.AreaFilters
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.areas.AreaCountry
import ru.practicum.android.diploma.domain.models.areas.AreaSubject
import ru.practicum.android.diploma.domain.models.areas.AreasSearchResult
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.ui.region.model.RegionFragmentStatus

class RegionSelectionViewModel(
    private val regionInteractor: AreasInteractor,
    private val filtersInteractor: FiltersInteractor,
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
                        filtersInteractor.getFiltersFromSharedPrefsForAreas().apply {
                            if (this.countryId.isNotEmpty()) {
                                getRegionsByParent(result, this.countryId)
                            } else {
                                getAllRegions(result)
                            }
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

    private fun getRegionsByParent(areasResult: AreasSearchResult, parentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val notSortedRegions = areasResult.listSubject.filter { it.parentId == parentId }
            regions.addAll(notSortedRegions.sortedBy { it.name })
            _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(regions))
        }
    }

    private fun getAllRegions(areasResult: AreasSearchResult) {
        viewModelScope.launch(Dispatchers.IO) {
            regions.addAll(areasResult.listSubject.sortedBy { it.name })
            parentRegions.addAll(areasResult.listCountry)
            _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(regions))
        }
    }

    fun savePermanentFilterOnBackToPlaceOfWork() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefsForAreas().apply {
                if (this.countryId.isNotEmpty()) {
                    saveFilter(
                        AreaFilters(
                            countryId = this.countryId,
                            countryName = this.countryName,
                            regionId = this.regionId,
                            regionName = this.regionName,
                            callback = true
                        )
                    )
                }
            }
        }
    }

    private fun getParentFromRegion(parentId: String): AreaSubject? {
        return regions.find { it.id == parentId }
    }

    private fun getParent(parentId: String): AreaCountry {
        var findParentInCountry = parentRegions.find { it.id == parentId }
        if (findParentInCountry == null) {
            var listParent = getParentFromRegion(parentId)
            var oldParents: AreaSubject? = null
            while (true) {
                if (listParent != null) {
                    oldParents = listParent
                    listParent = getParentFromRegion(oldParents.parentId)
                } else {
                    if (oldParents != null) {
                        findParentInCountry = parentRegions.find { it.id == oldParents.parentId }
                        break
                    }
                }
            }
        }
        return findParentInCountry!!
    }

    private fun saveFilter(areaFilters: AreaFilters) {
        filtersInteractor.putFiltersInSharedPrefsForAreas(areaFilters)
    }

    fun setFilters(selectedRegionItem: AreaSubject) {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefsForAreas().apply {
                if (this.countryId.isNotEmpty()) {
                    saveFilter(
                        AreaFilters(
                            countryId = this.countryId,
                            countryName = this.countryName,
                            regionId = selectedRegionItem.id,
                            regionName = selectedRegionItem.name,
                            callback = true
                        )
                    )
                } else {
                    val parentCountry: AreaCountry = getParent(selectedRegionItem.parentId)
                    saveFilter(
                        AreaFilters(
                            countryId = parentCountry.id,
                            countryName = parentCountry.name,
                            regionId = selectedRegionItem.id,
                            regionName = selectedRegionItem.name,
                            callback = true
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
