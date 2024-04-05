package ru.practicum.android.diploma.domain.db

sealed interface FavoriteVacanciesIdState {

    data class SuccessfulRequest(val vacanciesIdArrayList: ArrayList<String>) : FavoriteVacanciesIdState
    data class FailedRequest(val error: String) : FavoriteVacanciesIdState

}
