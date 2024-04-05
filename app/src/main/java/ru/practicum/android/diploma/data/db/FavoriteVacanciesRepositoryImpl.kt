package ru.practicum.android.diploma.data.db

import android.database.sqlite.SQLiteException
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.converters.FavoriteVacancyDbConverter
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesIdState
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.db.FavoriteVacancyState
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails

class FavoriteVacanciesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val favoriteVacancyDbConverter: FavoriteVacancyDbConverter
) : FavoriteVacanciesRepository {
    override suspend fun insertFavoriteVacancy(vacancy: VacancyDetails) {
        val insertingVacancy = favoriteVacancyDbConverter.map(vacancy)
        appDatabase.favoriteVacanciesDao().insertFavoriteVacancy(insertingVacancy)
    }

    override suspend fun deleteFavoriteVacancy(vacancyId: String) {
        appDatabase.favoriteVacanciesDao().deleteFavoriteVacancy(vacancyId)
    }

    override fun getFavoriteVacancy(vacancyId: String): Flow<FavoriteVacancyState> = flow {
        try {
            val favoriteVacancyFromDataBase = appDatabase.favoriteVacanciesDao().getFavoriteVacancy(vacancyId)
            val convertedFavoriteVacancy =
                favoriteVacancyDbConverter.map(favoriteVacancyFromDataBase)
            emit(FavoriteVacancyState.SuccessfulRequest(vacancy = convertedFavoriteVacancy))
        } catch (e: SQLiteException) {
            Log.e("AAA", "$e")
            emit(FavoriteVacancyState.FailedRequest)
        }
    }

    override fun getFavoriteVacanciesId(): Flow<FavoriteVacanciesIdState> = flow {
        try {
            val favoriteVacanciesIdList = appDatabase.favoriteVacanciesDao().getFavoriteVacanciesId()
            val favoriteVacanciesIdArrayList = ArrayList<String>()
            favoriteVacanciesIdArrayList.addAll(favoriteVacanciesIdList)
            emit(FavoriteVacanciesIdState.SuccessfulRequest(vacanciesIdArrayList = favoriteVacanciesIdArrayList))
        } catch (e: SQLiteException) {
            emit(FavoriteVacanciesIdState.FailedRequest)
        }
    }
}
