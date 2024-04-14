package ru.practicum.android.diploma.presentation.region

import android.util.Log
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
import ru.practicum.android.diploma.domain.models.areas.AreaSubject
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.ui.region.model.RegionFragmentStatus
import ru.practicum.android.diploma.util.Utilities
import java.net.SocketTimeoutException

class RegionSelectionViewModel(
    private val regionInteractor: AreasInteractor,
    private val filtersInteractor: FiltersInteractor,
    private val utilities: Utilities
) : ViewModel() {
    private var requestText = ""
    private var job: Job? = null
    private val regions: ArrayList<AreaSubject> = ArrayList()

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

    fun clickDebounce(): Boolean {
        return utilities.eventDebounce(viewModelScope, CLICK_DEBOUNCE_DELAY_MILLIS)
    }

    fun search() {
        /*val filteredRegions: ArrayList<AreaSubject> = ArrayList()
        regions
        if (idParentRegionFromSavedData.isNotEmpty()) {
            val filtered: ArrayList<AreaSubject> = ArrayList()
            filtered.addAll(regions.filter { it.parentId == idParentRegionFromSavedData })
            _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(filtered))
        } else {
            regions.addAll(result.listSubject)
            _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(regions))
        }*/
    }

    fun getRegions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                regionInteractor.getAreas().collect { result ->
                    when (result.responseStatus) {
                        ResponseStatus.OK -> {
                            val idParentRegionFromSavedData = filtersInteractor.getFiltersFromSharedPrefs().countryId
                            if (idParentRegionFromSavedData.isNotEmpty()) {
                                val loadedRegions: ArrayList<AreaSubject> = ArrayList()
                                regions.addAll(result.listSubject.filter { it.parentId == idParentRegionFromSavedData })
                                _regionStateData.postValue(RegionFragmentStatus.ListOfRegions(regions))
                            } else {
                                regions.addAll(result.listSubject)
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
            } catch (e: SocketTimeoutException) {
                Log.d(ERROR_TAG, "ошибка: ${e.message}")
            }
        }
    }

    fun setFilters(selectedRegionItem: AreaSubject) {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefs().apply {
                filtersInteractor.putFiltersInSharedPrefs(
                    Filters(
                        countryId = selectedRegionItem.parentId,
                        countryName = this.countryName,
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

/*
areasInteractor.getAreas().collect { areasSearchResult ->
    when (areasSearchResult.responseStatus) {

    }

try {
    vacancyInteractor.searchVacancy(requestText, _page.value!!).collect { result ->
        when (result.responseStatus) {
            ResponseStatus.OK -> {
                if (_page.value!! == 0) {
                    list.clear()
                    list.addAll(result.results)
                    foundVacancies = result.found
                    _listOfVacancies.postValue(
                        MainFragmentStatus.ListOfVacancies(result.results)
                    )
                    maxPages = result.pages
                } else {
                    list.addAll(result.results)
                    _listOfVacancies.postValue(MainFragmentStatus.ListOfVacancies(list))
                }
            }

            ResponseStatus.BAD -> {
                list.clear()
                _liveData.postValue(MainFragmentStatus.Bad)
            }

            ResponseStatus.DEFAULT -> {
                _liveData.postValue(MainFragmentStatus.Default)
            }

            ResponseStatus.NO_CONNECTION -> {
                _liveData.postValue(MainFragmentStatus.NoConnection)
            }

            ResponseStatus.LOADING -> Unit
        }
    }
} catch (e: SocketTimeoutException) {
    Log.d(ERROR_TAG, "ошибка: ${e.message}")
    _liveData.postValue(MainFragmentStatus.ShowToastOnLoadingTrouble)
}
}*/
