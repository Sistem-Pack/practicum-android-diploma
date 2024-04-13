package ru.practicum.android.diploma.presentation.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.impl.AreasInteractor
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.areas.AreasSearchResult
import java.net.SocketTimeoutException

class SelectCountryViewModel(
    private val areasInteractor: AreasInteractor
) : ViewModel() {

    private val _countryStateData = MutableLiveData<AreasSearchResult>()
    val countryStateData: LiveData<AreasSearchResult> = _countryStateData

    fun showAreas() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAreas()
            } catch (e: SocketTimeoutException) {
            }
        }
    }

    private suspend fun getAreas() {
        areasInteractor.getAreas().collect { areasSearchResult ->
            when (areasSearchResult.responseStatus) {
                ResponseStatus.OK -> {
                    _countryStateData.postValue(areasSearchResult)
                }

                ResponseStatus.BAD -> {
                }

                ResponseStatus.NO_CONNECTION -> {

                }

                ResponseStatus.LOADING -> {}

                ResponseStatus.DEFAULT -> Unit
            }
        }
    }

}
