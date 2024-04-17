package ru.practicum.android.diploma.presentation.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.industry.Industry
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.ui.industry.IndustrySelectionScreenState
import java.util.ArrayList

class IndustrySelectionViewModel(
    private val filtersInteractor: FiltersInteractor,
    private val industryInteractor: IndustryInteractor,
) : ViewModel() {

    private val allIndustries = ArrayList<Industry>()
    private val industries = ArrayList<Industry>()
    private var savedSelectedIndustry = Industry("", "")
    private var filters = Filters(
        countryId = "",
        countryName = "",
        regionId = "",
        regionName = "",
        industryId = "",
        industryName = "",
        salary = 0,
        doNotShowWithoutSalarySetting = false
    )

    private val industrySelectionScreenStateLiveData = MutableLiveData<IndustrySelectionScreenState>()
    fun observeIndustrySelectionScreenState(): LiveData<IndustrySelectionScreenState> =
        industrySelectionScreenStateLiveData

    fun getIndustries() {
        industrySelectionScreenStateLiveData.value = IndustrySelectionScreenState.UploadingProcess
        viewModelScope.launch(Dispatchers.IO) {
            filters = filtersInteractor.getActualFilterFromSharedPrefs()
            savedSelectedIndustry = Industry(filters.industryId, filters.industryName)
            industryInteractor.getIndustry().collect { industrySearchResult ->
                when (industrySearchResult.responseStatus) {
                    ResponseStatus.OK -> {
                        allIndustries.clear()
                        allIndustries.addAll(industrySearchResult.industries)
                        allIndustries.sortBy { it.name }
                        industries.clear()
                        industries.addAll(allIndustries)
                        checkPresentingSelectedIndustryInIndustriesList()
                        industrySelectionScreenStateLiveData.postValue(
                            IndustrySelectionScreenState.IndustriesUploaded(
                                industries = industries,
                                selectedIndustryId = savedSelectedIndustry.id,
                            )
                        )
                    }

                    ResponseStatus.BAD -> {
                        industrySelectionScreenStateLiveData.postValue(
                            IndustrySelectionScreenState.FailedRequest
                        )
                    }

                    ResponseStatus.NO_CONNECTION -> {
                        industrySelectionScreenStateLiveData.postValue(
                            IndustrySelectionScreenState.NoConnection
                        )
                    }

                    ResponseStatus.LOADING, ResponseStatus.DEFAULT -> Unit
                }
            }
        }
    }

    fun saveSelectedIndustry(selectedIndustry: Industry) {
        savedSelectedIndustry = if (selectedIndustry.id == savedSelectedIndustry.id) {
            Industry("", "")
        } else {
            Industry(selectedIndustry.id, selectedIndustry.name)
        }
        industrySelectionScreenStateLiveData.value =
            IndustrySelectionScreenState.IndustriesUploaded(
                industries = industries,
                selectedIndustryId = savedSelectedIndustry.id,
            )
    }

    fun putSelectedIndustryInSharedPrefs() {
        filters = Filters(
            countryId = filters.countryId,
            countryName = filters.countryName,
            regionId = filters.regionId,
            regionName = filters.regionName,
            industryId = savedSelectedIndustry.id,
            industryName = savedSelectedIndustry.name,
            salary = filters.salary,
            doNotShowWithoutSalarySetting = filters.doNotShowWithoutSalarySetting
        )
        filtersInteractor.putActualFilterInSharedPrefs(filters)
    }

    fun industrySearch(textSearch: String) {
        industries.clear()
        allIndustries.forEach {
            if (it.name.contains(textSearch, ignoreCase = true)) {
                industries.add(it)
            }
        }
        industrySelectionScreenStateLiveData.value =
            IndustrySelectionScreenState.IndustriesUploaded(
                industries = industries,
                selectedIndustryId = savedSelectedIndustry.id,
            )
    }

    private fun checkPresentingSelectedIndustryInIndustriesList() {
        if (!allIndustries.contains(savedSelectedIndustry)) {
            savedSelectedIndustry = Industry("", "")
        }
        putSelectedIndustryInSharedPrefs()
    }

}
