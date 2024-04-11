package ru.practicum.android.diploma.data.sharedprefs

import android.content.SharedPreferences
import ru.practicum.android.diploma.app.COUNTRY_ID_KEY
import ru.practicum.android.diploma.app.DO_NOT_SHOW_WITHOUT_SALARY_SETTING_KEY
import ru.practicum.android.diploma.app.REGION_ID_KEY
import ru.practicum.android.diploma.app.SALARY_AMOUNT_KEY
import ru.practicum.android.diploma.domain.sharedprefs.FiltersRepository

class FiltersRepositoryImpl(private val sharedPrefs: SharedPreferences): FiltersRepository {


    override suspend fun getCountryId(): String {
        return sharedPrefs.getString(COUNTRY_ID_KEY, null) ?: ""
    }

    override suspend fun getRegionId(): String {
        return sharedPrefs.getString(REGION_ID_KEY, null) ?: ""
    }

    override suspend fun getSalaryAmount(): Int {
        return sharedPrefs.getInt(SALARY_AMOUNT_KEY, 0)
    }

    override suspend fun getDoNotShowWithoutSalarySetting(): Boolean {
        return sharedPrefs.getBoolean(DO_NOT_SHOW_WITHOUT_SALARY_SETTING_KEY, false)
    }

    override fun putCountryId(countryId: String) {
        sharedPrefs.edit()
            .putString(COUNTRY_ID_KEY, countryId)
            .apply()
    }

    override fun putRegionId(regionId: String) {
        sharedPrefs.edit()
            .putString(REGION_ID_KEY, regionId)
            .apply()
    }

    override fun putSalaryAmount(salary: Int) {
        sharedPrefs.edit()
            .putInt(SALARY_AMOUNT_KEY, salary)
            .apply()
    }

    override fun putDoNotShowWithoutSalarySetting(doNotShowWithoutSalarySetting: Boolean) {
        sharedPrefs.edit()
            .putBoolean(DO_NOT_SHOW_WITHOUT_SALARY_SETTING_KEY, doNotShowWithoutSalarySetting)
            .apply()
    }

    override fun clearAllSettings() {
        sharedPrefs.edit()
            .clear()
            .apply()
    }
}
