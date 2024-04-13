package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface FavoriteVacanciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteVacancy(vacancy: VacancyEntity)

    @Query("DELETE FROM vacancy_table WHERE vacancy_id=:vacancyId")
    suspend fun deleteFavoriteVacancy(vacancyId: String)

    @Query("SELECT * FROM vacancy_table WHERE vacancy_id=:vacancyId")
    suspend fun getFavoriteVacancy(vacancyId: String): VacancyEntity

    @Query("SELECT vacancy_id FROM vacancy_table")
    suspend fun getFavoriteVacanciesId(): List<String>
}
