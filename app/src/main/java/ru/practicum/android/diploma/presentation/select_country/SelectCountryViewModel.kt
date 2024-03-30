package ru.practicum.android.diploma.presentation.select_country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectCountryViewModel : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData
}
