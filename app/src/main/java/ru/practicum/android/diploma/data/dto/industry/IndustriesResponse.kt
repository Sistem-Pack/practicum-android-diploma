package ru.practicum.android.diploma.data.dto.industry

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.vacancy.IndustryDto

data class IndustriesResponse(
    val industries: List<IndustryDto>?
) : Response()
