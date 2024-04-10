package ru.practicum.android.diploma.presentation.vacancy

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
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.ui.vacancy.JobVacancyScreenState
import ru.practicum.android.diploma.util.Utilities
import java.util.Calendar

class JobVacancyViewModel(
    private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor,
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
    private val sharingInteractor: SharingInteractor,
    private val utils: Utilities
) : ViewModel() {

    private var isFavorite = false

    private val jobVacancyScreenStateLiveData = MutableLiveData<JobVacancyScreenState>()
    fun observeJobVacancyScreenState(): LiveData<JobVacancyScreenState> = jobVacancyScreenStateLiveData

    fun clickToFavorite(vacancy: VacancyDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite) {
                isFavorite = false
                val updatedVacancy = updateVacancy(vacancy, 0L)
                favoriteVacanciesInteractor.deleteFavoriteVacancy(updatedVacancy.vacancyId)
                jobVacancyScreenStateLiveData.postValue(JobVacancyScreenState.VacancyUploaded(updatedVacancy))
            } else {
                isFavorite = true
                val updatedVacancy = updateVacancy(vacancy, Calendar.getInstance().time.time)
                favoriteVacanciesInteractor.insertFavoriteVacancy(updatedVacancy)
                jobVacancyScreenStateLiveData.postValue(JobVacancyScreenState.VacancyUploaded(updatedVacancy))
            }
        }
    }

    fun showDetailVacancy(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            jobVacancyScreenStateLiveData.postValue(JobVacancyScreenState.UploadingProcess)
            checkIsFavorite(vacancyId)
            loadVacancy(vacancyId)
        }
    }

    fun shareURL(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharingInteractor.shareLink(vacancyId)
        }
    }

    fun sendMail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharingInteractor.sendEmail(email)
        }
    }

    fun makeCall(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharingInteractor.makeACall(phoneNumber)
        }
    }

    fun clickDebounce(): Boolean {
        return utils.eventDebounce(viewModelScope, CLICK_DEBOUNCE_DELAY)
    }

    private suspend fun checkIsFavorite(vacancyId: String) {
        favoriteVacanciesInteractor.getFavoriteVacanciesId().collect {
            when (it) {
                is FavoriteVacanciesIdState.FailedRequest -> Log.e("VacancyDetailsError", "ошибка: ${it.error}")
                is FavoriteVacanciesIdState.SuccessfulRequest -> isFavorite =
                    it.vacanciesIdArrayList.contains(vacancyId)
            }
        }
    }

    private suspend fun loadVacancy(vacancyId: String) {
        vacancyDetailsInteractor.vacancyDetails(vacancyId).collect { vacancyDetailsResult ->
            when (vacancyDetailsResult.responseStatus) {
                ResponseStatus.OK -> {
                    jobVacancyScreenStateLiveData.postValue(
                        JobVacancyScreenState.VacancyUploaded(
                            updateVacancy(vacancyDetailsResult.results!!, 0L)
                        )
                    )
                }
                ResponseStatus.BAD -> {
                    if (vacancyDetailsResult.resultServerCode == ABSENCE_CODE) {
                        checkNeedfulDeletingFromFavorites(vacancyId)
                        jobVacancyScreenStateLiveData.postValue(JobVacancyScreenState.FailedRequest(""))
                    } else {
                        Log.e("VacancyDetailsError", "Ошибка сервера. Пробуем загрузить вакансию из БД.")
                        getFavoriteVacancyFromDataBase(vacancyId)
                    }
                }
                ResponseStatus.NO_CONNECTION -> {
                    Log.e("VacancyDetailsError", "Нет связи. Пробуем загрузить вакансию из БД.")
                    getFavoriteVacancyFromDataBase(vacancyId)
                }
                ResponseStatus.LOADING -> jobVacancyScreenStateLiveData.postValue(JobVacancyScreenState.UploadingProcess)
                ResponseStatus.DEFAULT -> Unit
            }
        }
    }

    private suspend fun checkNeedfulDeletingFromFavorites(vacancyId: String) {
        if (isFavorite) {
            favoriteVacanciesInteractor.deleteFavoriteVacancy(vacancyId)
            Log.e("VacancyDetailsError", "Удалена неактуальная вакансия")
        }
    }

    private suspend fun getFavoriteVacancyFromDataBase(vacancyId: String) {
        favoriteVacanciesInteractor.getFavoriteVacancy(vacancyId).collect { favoriteVacancyState ->
            when (favoriteVacancyState) {
                is FavoriteVacancyState.SuccessfulRequest -> {
                    jobVacancyScreenStateLiveData.postValue(
                        JobVacancyScreenState.VacancyUploaded(
                            updateVacancy(
                                favoriteVacancyState.vacancy,
                                favoriteVacancyState.vacancy.vacancyIdInDatabase
                            )
                        )
                    )
                }

                is FavoriteVacancyState.FailedRequest -> jobVacancyScreenStateLiveData.postValue(
                    JobVacancyScreenState.FailedRequest(favoriteVacancyState.error)
                )

            }
        }
    }

    private fun updateVacancy(vacancy: VacancyDetails, addingTime: Long): VacancyDetails {
        return VacancyDetails(
            vacancyIdInDatabase = addingTime,
            vacancyId = vacancy.vacancyId,
            vacancyName = vacancy.vacancyName,
            employer = vacancy.employer,
            industry = vacancy.industry,
            country = vacancy.country,
            areaId = vacancy.areaId,
            areaRegion = vacancy.areaRegion,
            contactsEmail = vacancy.contactsEmail,
            contactsName = vacancy.contactsName,
            contactsPhones = vacancy.contactsPhones,
            description = vacancy.description,
            employmentType = vacancy.employmentType,
            experienceName = vacancy.experienceName,
            salary = vacancy.salary,
            keySkills = vacancy.keySkills,
            artworkUrl = vacancy.artworkUrl,
            isFavorite = isFavorite,
        )
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val ABSENCE_CODE = 404
    }

}
