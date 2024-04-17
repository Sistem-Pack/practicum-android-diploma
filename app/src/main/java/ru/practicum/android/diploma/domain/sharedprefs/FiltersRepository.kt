package ru.practicum.android.diploma.domain.sharedprefs

import ru.practicum.android.diploma.domain.models.AreaFilters
import ru.practicum.android.diploma.domain.models.Filters

interface FiltersRepository {
    suspend fun getActualFilterFromSharedPrefs(): Filters

    fun putActualFilterInSharedPrefs(filters: Filters)

    fun clearActualFilterInSharedPrefs()

    suspend fun getOldFilterFromSharedPrefs(): Filters

    fun putOldFilterInSharedPrefs(filters: Filters)

    suspend fun getStarSearchStatus(): Boolean

    fun putStarSearchStatus(value: Boolean)

    suspend fun getFiltersFromSharedPrefsForAreas(): AreaFilters

    fun putFiltersInSharedPrefsForAreas(filters: AreaFilters)

    fun clearAllFiltersInSharedPrefsForAreas()
}