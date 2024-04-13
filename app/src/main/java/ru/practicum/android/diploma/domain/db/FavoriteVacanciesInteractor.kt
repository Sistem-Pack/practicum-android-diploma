package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails

interface FavoriteVacanciesInteractor {

    suspend fun insertFavoriteVacancy(vacancy: VacancyDetails)

    suspend fun deleteFavoriteVacancy(vacancyId: String)

    fun getFavoriteVacancy(vacancyId: String): Flow<FavoriteVacancyState>

    fun getFavoriteVacanciesId(): Flow<FavoriteVacanciesIdState>

}
