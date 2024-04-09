package ru.practicum.android.diploma.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.search.VacancyInteractor
import ru.practicum.android.diploma.ui.main.model.MainFragmentStatus
import ru.practicum.android.diploma.util.Utilities

class MainViewModel(
    val vacancyInteractor: VacancyInteractor,
    val utilities: Utilities
) : ViewModel() {
    private var requestText = ""
    private var job: Job? = null
    private var list = ArrayList<Vacancy>()
    private var page: Int = 0
    private var maxPages = 0

    private val _listOfVacancies: MutableLiveData<MainFragmentStatus> = MutableLiveData(MainFragmentStatus.Default)
    val listOfVacancies: LiveData<MainFragmentStatus> = _listOfVacancies

    fun onDestroy() {
        job?.cancel()
    }

    fun changeRequestText(text: String) {
        requestText = text
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

    fun installPage(oldRequest: Boolean) {
        if (oldRequest) {
            page++
        } else {
            page = 0
        }

    }

    fun search() {
        _listOfVacancies.postValue(MainFragmentStatus.Loading)
        sendRequest()
    }


    private fun sendRequest() {
        viewModelScope.launch {
            vacancyInteractor
                .searchVacancy(requestText, page)
                .collect { result ->
                    if (page == 0) {
                        list.clear()
                        list.addAll(result.results)
                        _listOfVacancies.postValue(MainFragmentStatus.ListOfVacancies(result.results, result.found))
                        maxPages = result.pages
                    } else {
                        list.addAll(result.results)
                        _listOfVacancies.postValue(MainFragmentStatus.ListOfVacancies(list, result.found))
                    }
                }
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
