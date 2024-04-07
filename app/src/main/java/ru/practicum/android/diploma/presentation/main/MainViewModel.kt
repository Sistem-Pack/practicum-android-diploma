package ru.practicum.android.diploma.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.search.VacancyInteractor
import ru.practicum.android.diploma.util.Utilities

class MainViewModel(
    val vacancyInteractor: VacancyInteractor,
    val utilities: Utilities
) : ViewModel() {
    private var requestText = ""
    private var job: Job? = null

    private val _foundVacancies: MutableLiveData<VacancySearchResult> =
        MutableLiveData(VacancySearchResult(emptyList<Vacancy>(), ResponseStatus.DEFAULT, 0, 0, 0))
    val foundVacancies: LiveData<VacancySearchResult> = _foundVacancies

    fun onDestroy() {
        job?.cancel()
    }

    fun changeRequestText(text: String) {
        requestText = text
    }

    fun searchDebounce() {
        job?.cancel()
        job = viewModelScope.launch {
            delay(2_000L)
            search()
        }
    }

    fun clickDebounce(): Boolean {
        return utilities.eventDebounce(viewModelScope, 1_000L)
    }

    private fun search() {
        _foundVacancies.postValue(VacancySearchResult(emptyList<Vacancy>(), ResponseStatus.LOADING, 0, 0, 0))
        sendRequest()
    }

    private fun sendRequest() {
        viewModelScope.launch {
            vacancyInteractor
                .searchVacancy(requestText)
                .collect { result ->
                    _foundVacancies.postValue(result)
                }
        }
    }

}



