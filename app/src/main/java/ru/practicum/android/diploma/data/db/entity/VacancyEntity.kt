package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id_in_database")
    val vacancyIdInDatabase: Long, // ID вакансии в базе данных создается по времени добавления
    @ColumnInfo(name = "vacancy_id")
    val vacancyId: String, // ID вакансии на сервере
    @ColumnInfo(name = "vacancy_name")
    val vacancyName: String, // Название профессии
    @ColumnInfo(name = "employer")
    val employer: String, // Работодатель
    @ColumnInfo(name = "industry")
    val industry: String, // Отрасль
    @ColumnInfo(name = "country")
    val country: String, // Страна
    @ColumnInfo(name = "area_id")
    val areaId: String, // Id региона
    @ColumnInfo(name = "area_region")
    val areaRegion: String, // Регион
    @ColumnInfo(name = "contacts_email")
    val contactsEmail: String, // Контакты Эл.почта
    @ColumnInfo(name = "contacts_name")
    val contactsName: String, // Контактное лицо
    @ColumnInfo(name = "contacts_phones")
    val contactsPhones: String, // Контакты Телефоны
    @ColumnInfo(name = "description")
    val description: String, // Описание вакансии
    @ColumnInfo(name = "employment_type")
    val employmentType: String, // Тип занятости
    @ColumnInfo(name = "experience_name")
    val experienceName: String, // Опыт работы
    @ColumnInfo(name = "salary")
    val salary: String, // ЗП
    @ColumnInfo(name = "key_skills")
    val keySkills: String, // Ключевые обязанности
    @ColumnInfo(name = "artwork_url")
    val artworkUrl: String, // Изображение
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean, // В избранном
)
