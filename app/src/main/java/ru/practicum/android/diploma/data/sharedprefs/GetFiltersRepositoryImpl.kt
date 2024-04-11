package ru.practicum.android.diploma.data.sharedprefs

import android.content.SharedPreferences
import ru.practicum.android.diploma.app.COUNTRY_ID_KEY
import ru.practicum.android.diploma.app.DO_NOT_SHOW_WITHOUT_SALARY_SETTING_KEY
import ru.practicum.android.diploma.app.INDUSTRY_ID_KEY
import ru.practicum.android.diploma.app.REGION_ID_KEY
import ru.practicum.android.diploma.app.SALARY_AMOUNT_KEY
import ru.practicum.android.diploma.domain.sharedprefs.GetFiltersRepository

class GetFiltersRepositoryImpl(private val sharedPrefs: SharedPreferences) : GetFiltersRepository {

    override suspend fun getCountryId(): String {
        return sharedPrefs.getString(COUNTRY_ID_KEY, null) ?: ""
    }

    override suspend fun getRegionId(): String {
        return sharedPrefs.getString(REGION_ID_KEY, null) ?: ""
    }

    override suspend fun getIndustryId(): String {
        return sharedPrefs.getString(INDUSTRY_ID_KEY, null) ?: ""
    }

    override suspend fun getSalaryAmount(): Int {
        return sharedPrefs.getInt(SALARY_AMOUNT_KEY, 0)
    }

    override suspend fun getDoNotShowWithoutSalarySetting(): Boolean {
        return sharedPrefs.getBoolean(DO_NOT_SHOW_WITHOUT_SALARY_SETTING_KEY, false)
    }

}
