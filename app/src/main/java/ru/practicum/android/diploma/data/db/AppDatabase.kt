package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Database(
    version = 3,
    entities = [VacancyEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacanciesDao(): FavoriteVacanciesDao
}
