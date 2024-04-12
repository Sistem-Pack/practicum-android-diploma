package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.data.dto.Response

data class IndustriesResponse(
    val industries: List<IndustryDto>?
) : Response()
