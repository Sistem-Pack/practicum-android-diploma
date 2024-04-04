package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.data.dto.Response

class VacancyResponse(
    val vacancies: List<VacancyDto>?,
    val resultCount: Int?
) : Response()
