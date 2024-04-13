package ru.practicum.android.diploma.domain.models.vacancy

data class VacancyDetails(
    val vacancyId: String, // ID вакансии на сервере
    val vacancyName: String, // Название профеcсии
    val employer: String, // Работодатель
    val industry: String, // Отрасль
    val country: String, // Страна
    val areaId: String, // Id региона
    val areaRegion: String, // Регион
    val contactsEmail: String, // Контакты Эл.почта
    val contactsName: String, // Контактное лицо
    val contactsPhones: String, // Контакты Телефоны
    val description: String, // Описание вакансии
    val employmentType: String, // Тип занятости
    val experienceName: String, // Опыт работы
    val salary: String, // ЗП
    val keySkills: String, // Ключевые обязанности
    val artworkUrl: String, // Изображение
    val isFavorite: Boolean = false, // В избранном
    val vacancyIdInDatabase: Long = 0L, // ID вакансии в БД
)
