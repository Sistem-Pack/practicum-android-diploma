package ru.practicum.android.diploma.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy

class MainViewModel : ViewModel() {

    private var clickJob: Job? = null

    private val _foundVacancies: MutableLiveData<VacancySearchResult> = MutableLiveData(VacancySearchResult(emptyList<Vacancy>(), ResponseStatus.DEFAULT, 0, 0, 0))
    val foundVacancies: LiveData<VacancySearchResult> = _foundVacancies



}



