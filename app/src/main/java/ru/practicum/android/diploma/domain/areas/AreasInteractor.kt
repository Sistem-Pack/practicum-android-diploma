package ru.practicum.android.diploma.domain.areas

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.areas.AreasSearchResult

interface AreasInteractor {
    fun getAreas(): Flow<AreasSearchResult>
}
