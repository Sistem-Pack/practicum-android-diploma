package ru.practicum.android.diploma.data.dto

class VacancyResponse(
    val vacancies: List<VacancyDto>?,
    val resultCount: Int?
) : Response()
