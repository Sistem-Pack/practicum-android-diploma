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
const val FILTERS_KEY = "key_for_filters"
const val FILTERS_OLD_KEY = "key_for_old_filter"
const val START_NEW_SEARCH = "key_for_start_new_search"
const val FILTERS_KEY_AREA = "key_for_filters_area"

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule, utilitiesModule)
        }
    }
}
