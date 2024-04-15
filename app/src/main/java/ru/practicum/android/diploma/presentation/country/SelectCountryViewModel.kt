package ru.practicum.android.diploma.presentation.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.models.AreaFilters
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.areas.AreaCountry
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.ui.country.model.CountryFragmentStatus

class SelectCountryViewModel(
    private val areasInteractor: AreasInteractor,
    private val filtersInteractor: FiltersInteractor,
) : ViewModel() {

    private val _countryStateData = MutableLiveData<CountryFragmentStatus>()
    val countryStateData: LiveData<CountryFragmentStatus> = _countryStateData

    fun showAreas() {
        viewModelScope.launch(Dispatchers.IO) {
            getAreas()
        }
    }

    private suspend fun getAreas() {
        areasInteractor.getAreas().collect { areasSearchResult ->
            when (areasSearchResult.responseStatus) {
                ResponseStatus.OK -> {
                    if (areasSearchResult.listCountry.isNotEmpty()) {
                        val areas: ArrayList<AreaCountry> = ArrayList()
                        areas.addAll(areasSearchResult.listCountry)
                        val allCountryItem = areas.find { it.name == ANYMORE_REGION_ITEM }
                        if (allCountryItem != null) {
                            areas.removeIf { it.name == ANYMORE_REGION_ITEM }
                            areas.add(allCountryItem)
                        }
                        _countryStateData.postValue(CountryFragmentStatus.ListOfCountries(areas))
                    } else {
                        _countryStateData.postValue(CountryFragmentStatus.NoLoaded)
                    }
                }

                ResponseStatus.BAD -> {
                    _countryStateData.postValue(CountryFragmentStatus.Bad)
                }

                ResponseStatus.NO_CONNECTION -> {
                    _countryStateData.postValue(CountryFragmentStatus.NoConnection)
                }

                ResponseStatus.LOADING, ResponseStatus.DEFAULT -> Unit

            }
        }
    }

    fun setFilters(selectedCountryItem: AreaCountry) {
        viewModelScope.launch(Dispatchers.IO) {
            filtersInteractor.getFiltersFromSharedPrefsForAreas().apply {
                filtersInteractor.putFiltersInSharedPrefsForAreas(
                    AreaFilters(
                        countryId = selectedCountryItem.id,
                        countryName = selectedCountryItem.name,
                        regionId = this.regionId,
                        regionName = this.regionName,
                        callback = true
                    )
                )
            }
        }
    }

    companion object {
        private const val ANYMORE_REGION_ITEM = "Другие регионы"
    }

}
