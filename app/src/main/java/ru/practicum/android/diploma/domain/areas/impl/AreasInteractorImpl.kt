package ru.practicum.android.diploma.domain.areas.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.domain.models.areas.AreasSearchResult

class AreasInteractorImpl(
    private val repository: AreasRepository
) : AreasInteractor {
    override fun getAreas(): Flow<AreasSearchResult> {
        return repository.getAreas()
    }
}
