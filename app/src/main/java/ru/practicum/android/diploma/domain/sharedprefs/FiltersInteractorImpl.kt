package ru.practicum.android.diploma.domain.sharedprefs

import ru.practicum.android.diploma.domain.models.Filters

class FiltersInteractorImpl(private val filtersRepository: FiltersRepository) : FiltersInteractor {

    override suspend fun getActualFilterFromSharedPrefs(): Filters {
        return filtersRepository.getActualFilterFromSharedPrefs()
    }

    override fun putActualFilterInSharedPrefs(filters: Filters) {
        return filtersRepository.putActualFilterInSharedPrefs(filters)
    }

    override fun clearActualFilterInSharedPrefs() {
        filtersRepository.clearActualFilterInSharedPrefs()
    }

    override suspend fun getOldFilterFromSharedPrefs(): Filters {
        return  filtersRepository.getOldFilterFromSharedPrefs()
    }

    override fun putOldFilterInSharedPrefs(filters: Filters) {
        filtersRepository.putOldFilterInSharedPrefs(filters)
    }

    override suspend fun getStarSearchStatus(): Boolean {
        return filtersRepository.getStarSearchStatus()
    }

    override fun putStarSearchStatus(value: Boolean) {
        filtersRepository.putStarSearchStatus(value)
    }
}
