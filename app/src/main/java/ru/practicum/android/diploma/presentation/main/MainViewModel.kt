package ru.practicum.android.diploma.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.search.VacancyInteractor
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.ui.main.model.MainFragmentStatus
import ru.practicum.android.diploma.util.Utilities

class MainViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val utilities: Utilities,
    private val filtersInteractorImpl: FiltersInteractor
) : ViewModel() {
    private var requestText = ""
    private var searchDebounceJob: Job? = null
    private var getDataFromSharedPrefsJob: Job? = null
    private var jobStarSearchStatus: Job? = null
    private var list = ArrayList<Vacancy>()
    private var foundVacancies: Int = 0
    private var maxPages: Int = 0
    private var filter: Filters = EMPTY_FILTER

    private val _listOfVacancies: MutableLiveData<MainFragmentStatus> = MutableLiveData(MainFragmentStatus.Default)
    val listOfVacancies: LiveData<MainFragmentStatus> = _listOfVacancies

    private var _page: MutableLiveData<Int> = MutableLiveData(0)
    val page: LiveData<Int> = _page

    private var _startNewSearch: MutableLiveData<Boolean> = MutableLiveData(false)
    val startNewSearch: LiveData<Boolean> = _startNewSearch

    fun onDestroy() {
        searchDebounceJob?.cancel()
        getDataFromSharedPrefsJob?.cancel()
        jobStarSearchStatus?.cancel()
    }

    fun breakSearch() {
        searchDebounceJob?.cancel()
    }

    fun getMaxPages(): Int {
        return maxPages
    }

    fun getFoundVacancies(): Int {
        return foundVacancies
    }

    fun changeRequestText(text: String) {
        requestText = text
    }

    fun getRequestText(): String {
        return requestText
    }

    fun searchDebounce() {
        searchDebounceJob?.cancel()
        searchDebounceJob = viewModelScope.launch(Dispatchers.IO) {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            search()
        }
    }

    fun clickDebounce(): Boolean {
        return utilities.eventDebounce(viewModelScope, CLICK_DEBOUNCE_DELAY_MILLIS)
    }

    fun scrollDebounce(): Boolean {
        return utilities.eventDebounce(viewModelScope, CLICK_DEBOUNCE_DELAY_MILLIS)
    }

    fun installPage(oldRequest: Boolean) {
        if (oldRequest) {
            _page.value = _page.value!! + 1
        } else {
            _page.value = 0
        }
    }

    fun search() {
        _listOfVacancies.postValue(MainFragmentStatus.Loading)
        sendRequest()
    }

    private fun sendRequest() {
        viewModelScope.launch(Dispatchers.IO) {
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
                        _listOfVacancies.postValue(MainFragmentStatus.Bad)
                    }

                    ResponseStatus.DEFAULT -> {
                        _listOfVacancies.postValue(MainFragmentStatus.Default)
                    }

                    ResponseStatus.NO_CONNECTION -> {
                        _listOfVacancies.postValue(MainFragmentStatus.NoConnection)
                    }

                    ResponseStatus.LOADING -> Unit
                }
            }
        } catch (e: SocketTimeoutException) {
                Log.d(ERROR_TAG, "ошибка: ${e.message}")
                _listOfVacancies.postValue(MainFragmentStatus.showToastOnLoadingTrouble)
            }
    }

    fun getFilterFromSharedPref() {
        viewModelScope.launch(Dispatchers.IO) {
            filter = filtersInteractorImpl.getActualFilterFromSharedPrefs()
            filtersInteractorImpl.putOldFilterInSharedPrefs(filter)
        }
    }

    fun checkForFilter(): Boolean {
        return filter.equals(EMPTY_FILTER)
    }

    fun getStarSearchStatusFromSharedPrefs() {
        getDataFromSharedPrefsJob = viewModelScope.launch(Dispatchers.IO) {
            _startNewSearch.postValue(filtersInteractorImpl.getStarSearchStatus())
        }
    }
    fun putStarSearchStatusInSharedPrefs(value: Boolean) {
        jobStarSearchStatus = viewModelScope.launch(Dispatchers.IO) {
            filtersInteractorImpl.putStarSearchStatus(value)
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val ERROR_TAG = "ErrorLoadingProcess"
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
