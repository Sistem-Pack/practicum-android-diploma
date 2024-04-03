package ru.practicum.android.diploma.domain.models

data class VacancyShort(
    val vacancyId: String, // ID вакансии
    val vacancyName: String, // Название проффесии
    val employer: String, // Работадатель
    val areaRegion: String, // Регион (в документации НН указано название города "Москва")
    val salaryCurrency: String?, // ЗП Валюта
    val salaryFrom: String?, // ЗП от
    val salaryTo: String?, // ЗП до
    val artworkUrl: String, // Изображение
)
