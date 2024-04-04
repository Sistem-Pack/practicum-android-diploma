package ru.practicum.android.diploma.domain.db

sealed interface FavoriteVacanciesIdState {

    object FailedRequest : FavoriteVacanciesIdState
    data class SuccessfulRequest(val vacanciesIdArrayList: ArrayList<String>) : FavoriteVacanciesIdState

}
