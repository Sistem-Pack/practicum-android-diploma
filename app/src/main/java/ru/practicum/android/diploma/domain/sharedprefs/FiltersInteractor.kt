package ru.practicum.android.diploma.domain.sharedprefs

import ru.practicum.android.diploma.domain.models.Filters

interface FiltersInteractor {
    suspend fun getFiltersFromSharedPrefs(): Filters?

    fun putFiltersInSharedPrefs(filters: Filters)

    fun clearAllFiltersInSharedPrefs()
}
