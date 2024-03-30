package ru.practicum.android.diploma.presentation.job_vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JobVacancyViewModel : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData
}
