package ru.practicum.android.diploma.data.area.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.areas.AreasRequest
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.areas.impl.AreasRepository
import ru.practicum.android.diploma.domain.models.areas.AreasSearchResult

class AreasRepositoryImpl(
    val networkClient: NetworkClient,
    val request: AreasRequest
): AreasRepository {
    override fun getAreas(): Flow<AreasSearchResult> {
        TODO("Not yet implemented")
    }
}
