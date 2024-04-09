package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.db.FavoriteVacancyState
import ru.practicum.android.diploma.domain.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacancyDetailsResult
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails

class JobVacancyViewModel(
    private val favoriteVacancyInteractor: FavoriteVacanciesInteractor,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
) : ViewModel() {

    private val _vacancyDetails: MutableLiveData<VacancyDetailsResult> = MutableLiveData<VacancyDetailsResult>()
    val vacancyDetails: LiveData<VacancyDetailsResult> = _vacancyDetails

    private val _checkIsFavorite: MutableLiveData<FavoriteVacancyState> = MutableLiveData<FavoriteVacancyState>()
    val checkIsFavorite: LiveData<FavoriteVacancyState> = _checkIsFavorite

    private val _shareUrl = MutableLiveData<String?>()
    val shareUrl: LiveData<String?> get() = _shareUrl

    fun showDetailVacancy(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            vacancyDetailsInteractor.vacancyDetails(vacancyId).collect {
                _vacancyDetails.postValue(it)
            }
        }
    }

    fun clickToFavorite(vacancy: VacancyDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteVacancyInteractor.insertFavoriteVacancy(vacancy)
        }
    }

    fun checkFavorite(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteVacancyInteractor.getFavoriteVacancy(vacancyId).collect {
                _checkIsFavorite.postValue(it)
            }
        }
    }

    fun shareURL(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}
