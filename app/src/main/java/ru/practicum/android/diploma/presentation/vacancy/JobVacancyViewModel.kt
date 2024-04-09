package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails

class JobVacancyViewModel(
    private val favoriteVacancyInteractor: FavoriteVacanciesInteractor,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
) : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData
    private var isFavourite = false
    private val _shareUrl = MutableLiveData<String?>()
    val shareUrl: LiveData<String?> get() = _shareUrl

    fun showDetailVacancy(vacancyid: String) {
        viewModelScope.launch {

        }
    }

    fun clickToFavorite(vacancy: VacancyDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            //isFavourite =
        }

    }

    fun checkFavorite(vacancyId: VacancyDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            //isFavourite =
        }
    }
}
