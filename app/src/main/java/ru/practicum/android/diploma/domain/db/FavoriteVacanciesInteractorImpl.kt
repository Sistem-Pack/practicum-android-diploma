package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails

class FavoriteVacanciesInteractorImpl(private val favoriteVacanciesRepository: FavoriteVacanciesRepository) :
    FavoriteVacanciesInteractor {

    override suspend fun insertFavoriteVacancy(vacancy: VacancyDetails) {
        favoriteVacanciesRepository.insertFavoriteVacancy(vacancy)
    }

    override suspend fun deleteFavoriteVacancy(vacancyId: String) {
        favoriteVacanciesRepository.deleteFavoriteVacancy(vacancyId)
    }

    override fun getFavoriteVacancy(vacancyId: String): Flow<FavoriteVacancyState> = flow {
        favoriteVacanciesRepository.getFavoriteVacancy(vacancyId).collect {
            emit(it)
        }
    }

    override fun getFavoriteVacanciesId(): Flow<FavoriteVacanciesIdState> = flow {
        favoriteVacanciesRepository.getFavoriteVacanciesId().collect {
            emit(it)
        }
    }

}
