package ru.practicum.android.diploma.domain.areas.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.areas.AreasSearchResult

interface AreasRepository {
    fun getAreas(): Flow<AreasSearchResult>
}
