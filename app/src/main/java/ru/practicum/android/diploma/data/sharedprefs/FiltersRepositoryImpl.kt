package ru.practicum.android.diploma.data.sharedprefs

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.app.FILTERS_KEY
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.sharedprefs.FiltersRepository

class FiltersRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson
) : FiltersRepository {

    override suspend fun getFiltersFromSharedPrefs(): Filters? {
        val filtersInSharedPrefs = sharedPrefs.getString(FILTERS_KEY, null)
        return if (filtersInSharedPrefs != null) {
            gson.fromJson(filtersInSharedPrefs, Filters::class.java)
        } else {
            null
        }
    }

    override fun putFiltersInSharedPrefs(filters: Filters) {
        if (filters.countryId == "" && filters.regionId == "" && filters.industryId == "" && filters.salary == 0 && !filters.doNotShowWithoutSalarySetting) {
            sharedPrefs.edit()
                .clear()
                .apply()
        } else {
            sharedPrefs.edit()
                .putString(FILTERS_KEY, gson.toJson(filters))
                .apply()
        }
    }

    override fun clearAllFiltersInSharedPrefs() {
        sharedPrefs.edit()
            .clear()
            .apply()
    }

}
