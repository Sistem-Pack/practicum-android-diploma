package ru.practicum.android.diploma.domain.models.vacancy

data class Vacancy(
    val vacancyId: String, // ID вакансии
    val vacancyName: String, // Название проффесии
    val employer: String, // Работадатель
    val areaRegion: String, // Регион (в документации НН указано название города "Москва")
    val salaryCurrency: String, // ЗП Валюта
    val salaryFrom: String, // ЗП от
    val salaryTo: String, // ЗП до
    val artworkUrl: String, // Изображение
)
