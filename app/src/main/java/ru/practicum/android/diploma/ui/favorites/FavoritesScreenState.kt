package ru.practicum.android.diploma.ui.favorites

import ru.practicum.android.diploma.domain.models.vacancy.Vacancy

sealed interface FavoritesScreenState {

    data class VacanciesUploaded(val vacancies: ArrayList<Vacancy>) : FavoritesScreenState

    data class VacanciesIdUploaded(val vacanciesId: ArrayList<String>) : FavoritesScreenState

    data class FailedRequest(val error: String) : FavoritesScreenState

    object NoFavoritesVacancies : FavoritesScreenState

    object UploadingProcess : FavoritesScreenState

}
