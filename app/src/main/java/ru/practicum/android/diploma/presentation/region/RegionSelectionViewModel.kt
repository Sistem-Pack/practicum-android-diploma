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
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.util.Utilities

class RegionSelectionViewModel(
    private val regionInteractor: AreasInteractor,
    private val filtersInteractor: FiltersInteractor,
    private val utilities: Utilities
) : ViewModel() {
    private var requestText = ""
    private var job: Job? = null
    private var list = ArrayList<Vacancy>()

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

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
        sendRequest()
    }

    private fun sendRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            val countryRegionId: String = filtersInteractor.getFiltersFromSharedPrefs().countryId
            regionInteractor.getAreas().collect { result ->
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
