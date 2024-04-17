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
        currentFilter = null
        oldFilter = null
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
            var countryId: String = ""
            var countryName: String = ""
            var regionId: String = ""
            var regionName: String = ""
            var industryId: String = ""
            var industryName: String = ""

            if (!jobPlaceEmpty) {
                countryId = _filter.value!!.countryId
                countryName = _filter.value!!.countryName
                regionId = _filter.value!!.regionId
                regionName = _filter.value!!.regionName
            }
            if (!industryIsEmpty) {
                industryId = _filter.value!!.industryId
                industryName = _filter.value!!.industryName
            }

            val newFilter = Filters(
                countryId = countryId,
                countryName = countryName,
                regionId = regionId,
                regionName = regionName,
                industryId = industryId,
                industryName = industryName,
                salary = if (salary.isEmpty()) 0 else salary.toInt(),
                doNotShowWithoutSalarySetting = doNotShowWithoutSalary
            )
            currentFilter = newFilter
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

    fun turnSwitch() {
        if (_filter.value != null) {
            switch = true
        }
    }
}
