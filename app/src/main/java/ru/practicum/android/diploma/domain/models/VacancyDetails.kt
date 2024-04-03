package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val vacancyId: String, // ID вакансии
    val vacancyName: String, // Название проффесии
    val employer: String, // Работадатель
    val industry: String, // Отрасль
    val country: String, // Страна
    val areaId: String, // Id региона
    val areaRegion: String, // Регион
    val contactsEmail: String, // Контакты Эл.почта
    val contactsName: String, // Контактное лицо
    val contactsPhones: String, // Контакты Телефоны
    val description: String, // Описание вакансии
    val employmentName: String, // Тип занятости
    val experienceName: String, // Опыт работы
    val salaryCurrency: String?, // ЗП Валюта
    val salaryFrom: String?, // ЗП от
    val salaryTo: String?, // ЗП до
    val keySkills: String, // Ключевые обязанности
    val artworkUrl: String, // Изображение
    val isFavorite: Boolean = false // В избранном
)
