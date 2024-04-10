package ru.practicum.android.diploma.presentation.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesIdState
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.db.FavoriteVacancyState
import ru.practicum.android.diploma.domain.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacancyDetailsResult
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails
import ru.practicum.android.diploma.ui.favorites.FavoritesScreenState
import ru.practicum.android.diploma.util.Utilities

class FavoritesViewModel(
    private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val utils: Utilities
) :
    ViewModel() {

    private val vacanciesIdArrayList = ArrayList<String>()
    private val nextVacanciesList = ArrayList<Vacancy>()
    private val allVacanciesList = ArrayList<Vacancy>()
    private var workedVacancies = 0
    private var vacanciesListsQuantity = 0
    private var favoriteVacanciesIsLoading = false

    private val favoritesScreenStateLiveData =
        MutableLiveData<FavoritesScreenState>()

    fun observeFavoritesScreenState(): LiveData<FavoritesScreenState> = favoritesScreenStateLiveData

    fun getFavoriteVacanciesId() {
        viewModelScope.launch {
            favoriteVacanciesInteractor.getFavoriteVacanciesId().collect {
                when (it) {
                    is FavoriteVacanciesIdState.FailedRequest -> favoritesScreenStateLiveData.postValue(
                        FavoritesScreenState.FailedRequest(it.error)
                    )

                    is FavoriteVacanciesIdState.SuccessfulRequest -> {
                        vacanciesIdArrayList.clear()
                        vacanciesIdArrayList.addAll(it.vacanciesIdArrayList)
                        favoritesScreenStateLiveData.postValue(
                            FavoritesScreenState.VacanciesIdUploaded(it.vacanciesIdArrayList)
                        )
                    }

                }
            }
        }
    }

    fun getFavoriteVacancies() {
        if (workedVacancies < vacanciesIdArrayList.size) {
            favoritesScreenStateLiveData.value =
                FavoritesScreenState.UploadingProcess
        }
        if (!favoriteVacanciesIsLoading) {
            loadFavoriteVacancies()
        }
    }

    fun clickDebounce(): Boolean {
        return utils.eventDebounce(viewModelScope, CLICK_DEBOUNCE_DELAY)
    }

    private fun loadFavoriteVacancies() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteVacanciesIsLoading = true
            if (workedVacancies < vacanciesIdArrayList.size) {
                vacanciesListsQuantity += 1
                nextVacanciesList.clear()
                var vacancyNumberInList = 1
                val vacanciesQuantityInNextVacanciesList = setVacanciesQuantityInNextVacanciesList()
                while (vacancyNumberInList <= vacanciesQuantityInNextVacanciesList) {
                    val vacancyIdPositionInList =
                        (vacanciesListsQuantity - 1) * MAX_LINES_ON_PAGE + vacancyNumberInList - 1
                    val vacancyId = vacanciesIdArrayList[vacancyIdPositionInList]
                    getFavoriteVacancy(vacancyId)
                    vacancyNumberInList += 1
                }
                workedVacancies += vacanciesQuantityInNextVacanciesList
                favoritesScreenStateLiveData.postValue(FavoritesScreenState.VacanciesUploaded(allVacanciesList))
            } else {
                doWhenAllVacanciesUploaded()
            }
            favoriteVacanciesIsLoading = false
        }
    }

    private fun setVacanciesQuantityInNextVacanciesList(): Int {
        return if (vacanciesIdArrayList.size - workedVacancies > MAX_LINES_ON_PAGE) {
            MAX_LINES_ON_PAGE
        } else {
            vacanciesIdArrayList.size - workedVacancies
        }
    }

    private suspend fun getFavoriteVacancy(vacancyId: String) {
        vacancyDetailsInteractor.vacancyDetails(vacancyId).collect { vacancyDetailsResult ->
            Log.e("AAA", "resultServerCode = ${vacancyDetailsResult.resultServerCode}")
            when (vacancyDetailsResult.responseStatus) {
                ResponseStatus.OK -> {
                    refreshFavoriteVacancyInDataBase(vacancyId, vacancyDetailsResult)
                    allVacanciesList.add(
                        Vacancy(
                            vacancyId = vacancyDetailsResult.results!!.vacancyId,
                            vacancyName = vacancyDetailsResult.results.vacancyName,
                            employer = vacancyDetailsResult.results.employer,
                            areaRegion = vacancyDetailsResult.results.areaRegion,
                            salary = vacancyDetailsResult.results.salary,
                            artworkUrl = vacancyDetailsResult.results.artworkUrl
                        )
                    )
                }

                ResponseStatus.BAD -> {
                    if (vacancyDetailsResult.resultServerCode == ABSENCE_CODE) {
                        favoriteVacanciesInteractor.deleteFavoriteVacancy(vacancyId)
                        Log.e("AAA", "Удалена неактуальная вакансия")
                    } else {
                        Log.e("AAA", "Ошибка сервера. Пробуем загрузить вакансию из БД.")
                        getFavoriteVacancyFromDataBase(vacancyId)
                    }
                }

                ResponseStatus.NO_CONNECTION -> {
                    Log.e("AAA", "Нет связи. Пробуем загрузить вакансию из БД.")
                    getFavoriteVacancyFromDataBase(vacancyId)
                }

                ResponseStatus.LOADING -> Unit
                ResponseStatus.DEFAULT -> Unit
            }
        }
    }

    private suspend fun getFavoriteVacancyFromDataBase(vacancyId: String) {
        favoriteVacanciesInteractor.getFavoriteVacancy(vacancyId).collect { favoriteVacancyState ->
            when (favoriteVacancyState) {
                is FavoriteVacancyState.SuccessfulRequest -> {
                    allVacanciesList.add(
                        Vacancy(
                            vacancyId = favoriteVacancyState.vacancy.vacancyId,
                            vacancyName = favoriteVacancyState.vacancy.vacancyName,
                            employer = favoriteVacancyState.vacancy.employer,
                            areaRegion = favoriteVacancyState.vacancy.areaRegion,
                            salary = favoriteVacancyState.vacancy.salary,
                            artworkUrl = favoriteVacancyState.vacancy.artworkUrl
                        )
                    )
                }

                is FavoriteVacancyState.FailedRequest -> Log.e(
                    "AAA",
                    "Ошибка получения вакансии из БД: ${favoriteVacancyState.error}"
                )

            }
        }
    }

    private suspend fun refreshFavoriteVacancyInDataBase(
        vacancyId: String,
        vacancyDetailsResult: VacancyDetailsResult
    ) {
        var vacancyIdInDatabase = 0L
        favoriteVacanciesInteractor.getFavoriteVacancy(vacancyId).collect {
            if (it is FavoriteVacancyState.SuccessfulRequest) {
                vacancyIdInDatabase = it.vacancy.vacancyIdInDatabase
            }
        }
        favoriteVacanciesInteractor.deleteFavoriteVacancy(vacancyId)
        favoriteVacanciesInteractor.insertFavoriteVacancy(
            VacancyDetails(
                vacancyIdInDatabase = vacancyIdInDatabase,
                vacancyId = vacancyDetailsResult.results!!.vacancyId,
                vacancyName = vacancyDetailsResult.results.vacancyName,
                employer = vacancyDetailsResult.results.employer,
                industry = vacancyDetailsResult.results.industry,
                country = vacancyDetailsResult.results.country,
                areaId = vacancyDetailsResult.results.areaId,
                areaRegion = vacancyDetailsResult.results.areaRegion,
                contactsEmail = vacancyDetailsResult.results.contactsEmail,
                contactsName = vacancyDetailsResult.results.contactsName,
                contactsPhones = vacancyDetailsResult.results.contactsPhones,
                description = vacancyDetailsResult.results.description,
                employmentType = vacancyDetailsResult.results.employmentType,
                experienceName = vacancyDetailsResult.results.experienceName,
                salary = vacancyDetailsResult.results.salary,
                keySkills = vacancyDetailsResult.results.keySkills,
                artworkUrl = vacancyDetailsResult.results.artworkUrl,
                isFavorite = true,
            )
        )
    }

    private fun doWhenAllVacanciesUploaded() {
        if (allVacanciesList.isEmpty()) {
            favoritesScreenStateLiveData.postValue(FavoritesScreenState.FailedRequest(""))
        } else {
            favoritesScreenStateLiveData.postValue(FavoritesScreenState.VacanciesUploaded(allVacanciesList))
        }
    }

    companion object {
        private const val MAX_LINES_ON_PAGE = 20
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val ABSENCE_CODE = 404
    }

}
