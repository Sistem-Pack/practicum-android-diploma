package ru.practicum.android.diploma.data.dto.areas

import ru.practicum.android.diploma.domain.models.ResponseStatus

data class AreasResponse(
    val areas: List<AreasDto>,
    val resultResponseStatus: ResponseStatus = ResponseStatus.DEFAULT
)
