package ru.practicum.android.diploma.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.utilitiesModule
import ru.practicum.android.diploma.di.viewModelModule

const val PRACTICUM_DIPLOMA_PREFERENCES = "practicum_diploma_preferences"
const val COUNTRY_ID_KEY = "key_for_country_id"
const val REGION_ID_KEY = "key_for_region_id"
const val INDUSTRY_ID_KEY = "key_for_industry_id"
const val SALARY_AMOUNT_KEY = "key_for_salary_amount"
const val DO_NOT_SHOW_WITHOUT_SALARY_SETTING_KEY = "key_for_do_not_show_without_salary_setting"

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule, utilitiesModule)
        }
    }
}
