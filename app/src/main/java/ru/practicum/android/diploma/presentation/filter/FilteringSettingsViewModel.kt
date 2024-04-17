package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor

class FilteringSettingsViewModel(
    private val filtersInteractorImpl: FiltersInteractor
) : ViewModel() {
    private var jobGet: Job? = null
    private var jobClear: Job? = null
    private var jobPut: Job? = null
    private var jobStarSearchStatus: Job? = null
    private var currentFilter: Filters? = null
    private var oldFilter: Filters? = null
    private var switch: Boolean = false

    private val _filter: MutableLiveData<Filters?> = MutableLiveData(null)
    val filter: LiveData<Filters?> = _filter

    fun onCreate() {
        switch = false
        jobGet = viewModelScope.launch(Dispatchers.IO) {
            currentFilter = filtersInteractorImpl.getActualFilterFromSharedPrefs()
            oldFilter = filtersInteractorImpl.getOldFilterFromSharedPrefs()
            _filter.postValue(currentFilter)
        }
    }

    fun onDestroy() {
        jobGet?.cancel()
        jobClear?.cancel()
        jobPut?.cancel()
        jobStarSearchStatus?.cancel()
    }

    fun makeCurrentFilter(
        jobPlaceEmpty: Boolean,
        industryIsEmpty: Boolean,
        salary: String,
        doNotShowWithoutSalary: Boolean
    ) {
        if (switch) {
            var filter = Filters(
                countryId = if (jobPlaceEmpty) "" else _filter.value!!.countryId,
                countryName = if (jobPlaceEmpty) "" else _filter.value!!.countryName,
                regionId = if (jobPlaceEmpty) "" else _filter.value!!.regionId,
                regionName = if (jobPlaceEmpty) "" else _filter.value!!.regionName,
                industryId = if (industryIsEmpty) "" else _filter.value!!.industryId,
                industryName = if (industryIsEmpty) "" else _filter.value!!.industryName,
                salary = if (salary.isEmpty()) 0 else salary.toInt(),
                doNotShowWithoutSalarySetting = doNotShowWithoutSalary
            )
            currentFilter = filter
            putFilterInSharedPrefs()
        }
    }

    private fun putFilterInSharedPrefs() {
        jobPut = viewModelScope.launch(Dispatchers.IO) {
            filtersInteractorImpl.putActualFilterInSharedPrefs(currentFilter!!)
        }
    }

    fun putStarSearchStatusInSharedPrefs(value: Boolean) {
        jobStarSearchStatus = viewModelScope.launch(Dispatchers.IO) {
            filtersInteractorImpl.putStarSearchStatus(value)
        }
    }

    fun changeOldFilterInSharedPrefs() {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractorImpl.putOldFilterInSharedPrefs(currentFilter!!)
        }
    }

    fun compareFilters(): Boolean {
        if (oldFilter == null || currentFilter == null) return false
        return !currentFilter!!.equals(oldFilter)
    }

    fun turnSwitch(){
        if (_filter.value != null){
            switch = true
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
