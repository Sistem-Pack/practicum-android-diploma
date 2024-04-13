package ru.practicum.android.diploma.domain.areas.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.areas.AreasSearchResult

class AreasInteractorImpl(
    val repository: AreasRepository
) : AreasInteractor {
    override fun getAreas(): Flow<AreasSearchResult> {
        return repository.getAreas()
    }
}
