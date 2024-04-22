package ru.practicum.android.diploma.data.dto.industry

import ru.practicum.android.diploma.domain.models.ResponseStatus

data class IndustriesResponse(
    val industries: List<IndustryDto>,
    val resultResponseStatus: ResponseStatus = ResponseStatus.DEFAULT,
    val resultCode: Int = 0,
)
