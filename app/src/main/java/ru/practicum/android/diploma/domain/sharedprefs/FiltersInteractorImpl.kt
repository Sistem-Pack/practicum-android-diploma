package ru.practicum.android.diploma.domain.sharedprefs

import ru.practicum.android.diploma.domain.models.Filters

class FiltersInteractorImpl(private val filtersRepository: FiltersRepository) : FiltersInteractor {

    override suspend fun getFiltersFromSharedPrefs(): Filters {
        return filtersRepository.getFiltersFromSharedPrefs()
    }

    override fun putFiltersInSharedPrefs(filters: Filters) {
        return filtersRepository.putFiltersInSharedPrefs(filters)
    }

    override fun clearAllFiltersInSharedPrefs() {
        filtersRepository.clearAllFiltersInSharedPrefs()
    }
}
