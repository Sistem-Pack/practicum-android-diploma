package ru.practicum.android.diploma.data.sharedprefs

import android.content.SharedPreferences
import ru.practicum.android.diploma.app.COUNTRY_ID_KEY
import ru.practicum.android.diploma.app.DO_NOT_SHOW_WITHOUT_SALARY_SETTING_KEY
import ru.practicum.android.diploma.app.INDUSTRY_ID_KEY
import ru.practicum.android.diploma.app.REGION_ID_KEY
import ru.practicum.android.diploma.app.SALARY_AMOUNT_KEY
import ru.practicum.android.diploma.domain.sharedprefs.PutFiltersRepository

class PutFiltersRepositoryImpl(private val sharedPrefs: SharedPreferences) : PutFiltersRepository {
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

    override fun putIndustryId(industryId: String) {
        sharedPrefs.edit()
            .putString(INDUSTRY_ID_KEY, industryId)
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
