package ru.practicum.android.diploma.presentation.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.presentation.main.MainViewModel

class FilteringSettingsViewModel(
    private val filtersInteractorImpl: FiltersInteractor
) : ViewModel() {
    private var jobGet: Job? = null
    private var jobClear: Job? = null
    private var jobPut: Job? = null

    private val _filter = MutableLiveData(EMPTY_FILTER)
    val liveData: LiveData<Filters> = _filter
    private val _filterDataIsChange = MutableLiveData(false)
    val filterDataIsChange: LiveData<Boolean> = _filterDataIsChange

    fun onCreate() {
        var savedFilter: Filters = EMPTY_FILTER
        jobGet = viewModelScope.launch {
            savedFilter = filtersInteractorImpl.getFiltersFromSharedPrefs()
            Log.d("MY_TAG", "job thread")
        }
        Log.d("MY_TAG", "after job thread")
        if (savedFilter.equals(EMPTY_FILTER)) {
            return
        }
        if (_filter.value!!.equals(EMPTY_FILTER) && !savedFilter.equals(EMPTY_FILTER)) {
            _filter.value = savedFilter
            return
        }
        if (!savedFilter.equals(_filter.value!!)) {
            _filterDataIsChange.value = true
            _filter.value = savedFilter
            // надо заполнить поля фрагмента
            // отобразить кнопки
            //
        }
    }

    fun onDestroy() {
        jobGet?.cancel()
        jobClear?.cancel()
        jobPut?.cancel()
    }

    fun putFilterInSharedPrefs(salary: String, doNotShowWithoutSalary: Boolean) {
        jobPut = viewModelScope.launch {
            filtersInteractorImpl.putFiltersInSharedPrefs(
                Filters(
                    countryId = _filter.value!!.countryId,
                    countryName = _filter.value!!.countryName,
                    regionId = _filter.value!!.regionId,
                    regionName = _filter.value!!.regionName,
                    industryId = _filter.value!!.industryId,
                    industryName = _filter.value!!.industryName,
                    salary = if (salary.isEmpty()) 0 else salary.toInt(),
                    doNotShowWithoutSalarySetting = doNotShowWithoutSalary
                )
            )
        }
        _filterDataIsChange.value = true
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
