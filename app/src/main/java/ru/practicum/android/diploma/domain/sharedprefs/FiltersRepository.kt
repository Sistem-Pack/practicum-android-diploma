package ru.practicum.android.diploma.domain.sharedprefs

import ru.practicum.android.diploma.domain.models.AreaFilters
import ru.practicum.android.diploma.domain.models.Filters

interface FiltersRepository {
    suspend fun getFiltersFromSharedPrefs(): Filters

    fun putFiltersInSharedPrefs(filters: Filters)

    fun clearAllFiltersInSharedPrefs()

    suspend fun getFiltersFromSharedPrefsForAreas(): AreaFilters

    fun putFiltersInSharedPrefsForAreas(filters: AreaFilters)

    fun clearAllFiltersInSharedPrefsForAreas()
}
