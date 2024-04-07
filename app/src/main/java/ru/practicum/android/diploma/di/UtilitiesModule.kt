package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.util.SalaryInfo
import ru.practicum.android.diploma.util.Utilities

val utilitiesModule = module {
    factory {
        Utilities(androidContext())
    }

    factory {
        SalaryInfo(androidContext())
    }
}
