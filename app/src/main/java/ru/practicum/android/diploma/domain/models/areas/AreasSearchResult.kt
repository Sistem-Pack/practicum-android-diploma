package ru.practicum.android.diploma.domain.models.areas

import ru.practicum.android.diploma.domain.models.ResponseStatus

data class AreasSearchResult(
    val listCountry: List<AreaCountry>,
    val listSubject: List<AreaSubject>,
    val responseStatus: ResponseStatus
)
