package ru.practicum.android.diploma.domain.models.industry

import ru.practicum.android.diploma.domain.models.ResponseStatus

data class IndustrySearchResult(
    val industries: List<Industry>,
    val responseStatus: ResponseStatus
)
