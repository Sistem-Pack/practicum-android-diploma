package ru.practicum.android.diploma.data.sharedprefs

import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.sharedprefs.FiltersRepository
import ru.practicum.android.diploma.domain.models.AreaFilters
import ru.practicum.android.diploma.app.FILTERS_KEY
import ru.practicum.android.diploma.app.FILTERS_OLD_KEY
import ru.practicum.android.diploma.app.START_NEW_SEARCH
import ru.practicum.android.diploma.app.FILTERS_KEY_AREA
import com.google.gson.Gson
import android.content.SharedPreferences

class FiltersRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson
) : FiltersRepository {

    private val emptyFilters = Filters(
        countryId = "",
        countryName = "",
        regionId = "",
        regionName = "",
        industryId = "",
        industryName = "",
        salary = 0,
        doNotShowWithoutSalarySetting = false
    )

    private val emptyAreaFilters = AreaFilters(
        countryId = "",
        countryName = "",
        regionId = "",
        regionName = ""
    )

    override suspend fun getActualFilterFromSharedPrefs(): Filters {
        val filtersInSharedPrefs = sharedPrefs.getString(FILTERS_KEY, null)
        return if (filtersInSharedPrefs != null) {
            gson.fromJson(filtersInSharedPrefs, Filters::class.java)
        } else {
            emptyFilters
        }
    }

    override fun putActualFilterInSharedPrefs(filters: Filters) {
        sharedPrefs.edit()
            .putString(FILTERS_KEY, gson.toJson(filters))
            .apply()
    }

    override fun clearActualFilterInSharedPrefs() {
        sharedPrefs.edit()
            .putString(FILTERS_KEY, null)
            .apply()
    }

    override fun putOldFilterInSharedPrefs(filters: Filters) {
        sharedPrefs.edit()
            .putString(FILTERS_OLD_KEY, gson.toJson(filters))
            .apply()
    }

    override suspend fun getOldFilterFromSharedPrefs(): Filters {
        val filtersInSharedPrefs = sharedPrefs.getString(FILTERS_OLD_KEY, null)
        return if (filtersInSharedPrefs != null) {
            gson.fromJson(filtersInSharedPrefs, Filters::class.java)
        } else {
            emptyFilters
        }
    }

    override fun putStarSearchStatus(value: Boolean) {
        sharedPrefs.edit()
            .putString(START_NEW_SEARCH, gson.toJson(value))
            .apply()
    }

    override suspend fun getStarSearchStatus(): Boolean {
        val filtersInSharedPrefs = sharedPrefs.getString(START_NEW_SEARCH, null)
        return if (filtersInSharedPrefs != null) {
            gson.fromJson(filtersInSharedPrefs, Boolean::class.java)
        } else {
            false
        }
    }

    override suspend fun getFiltersFromSharedPrefsForAreas(): AreaFilters {
        val filtersInSharedPrefs = sharedPrefs.getString(FILTERS_KEY_AREA, null)
        return if (filtersInSharedPrefs != null) {
            gson.fromJson(filtersInSharedPrefs, AreaFilters::class.java)
        } else {
            emptyAreaFilters
        }
    }

    override fun putFiltersInSharedPrefsForAreas(filters: AreaFilters) {
        sharedPrefs.edit()
            .putString(FILTERS_KEY_AREA, gson.toJson(filters))
            .apply()
    }

    override fun clearAllFiltersInSharedPrefsForAreas() {
        sharedPrefs.edit()
            .putString(FILTERS_KEY_AREA, gson.toJson(emptyAreaFilters))
            .apply()
    }
}
