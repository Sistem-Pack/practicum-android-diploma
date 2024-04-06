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
import ru.practicum.android.diploma.domain.search.VacancyInteractor

class MainViewModel(
    val vacancyInteractor: VacancyInteractor
) : ViewModel() {
    private var requestText = ""
    private var isClickAllowed = true
    private var clickJob: Job? = null
    private var searchJob: Job? = null

    private val _liveData = MutableLiveData<VacancySearchResult>()
    val liveData: LiveData<VacancySearchResult> = _liveData

    fun changeRequestText(text: String) {
        requestText = text
    }

    fun searchDebounce() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(2000L)
            search()
        }
    }

    private fun search() {
        _liveData.postValue(getLoadingStatus())
        sendRequest()
    }

    private fun sendRequest() {
        viewModelScope.launch {
            vacancyInteractor
                .searchVacancy(requestText)
                .collect { result ->
                    _liveData.postValue(result)
                }
        }
    }
    private fun getLoadingStatus(): VacancySearchResult {
        return VacancySearchResult(
            results = emptyList(),
            ResponseStatus.DEFAULT,
            0,
            0,
            0
        )
    }
}
