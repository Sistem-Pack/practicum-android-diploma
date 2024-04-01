package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id")
    val vacancyId: String,                    //ID вакансии
    @ColumnInfo(name = "vacancy_name")
    val vacancyName: String,                //Название проффесии
    @ColumnInfo(name = "industry")
    val industry: String,                    //Отрасль
    @ColumnInfo(name = "country")
    val country: String,                    //Страна
    @ColumnInfo(name = "area_id")
    val areaId: String,                        //Id региона
    @ColumnInfo(name = "area_region")
    val areaRegion: String,                    //Регион
    @ColumnInfo(name = "contacts-email")
    val contactsEmail: String,                //Контакты Эл.почта
    @ColumnInfo(name = "contacts-name")
    val contactsName: String,                //Контактное лицо
    @ColumnInfo(name = "contacts-phones")
    val contactsPhones: String,                //Контакты Телефоны
    @ColumnInfo(name = "description")
    val description: String,                //Описание вакансии
    @ColumnInfo(name = "employment_name")
    val employmentName: String,                //Тип занятости
    @ColumnInfo(name = "experience_name")
    val experienceName: String,                //Опыт работы
    @ColumnInfo(name = "salary_currency")
    val salaryCurrency: String,                //ЗП Валюта
    @ColumnInfo(name = "salary_from")
    val salaryFrom: Int,                    //ЗП от
    @ColumnInfo(name = "salary_to")
    val salaryTo: Int,                        //ЗП до
    @ColumnInfo(name = "key_skills")
    val keySkills: String,                    //Ключевые обязанности
    @ColumnInfo(name = "artwork_url")
    val artworkUrl: String,                    //Изображение
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean                    //В избранном

)
