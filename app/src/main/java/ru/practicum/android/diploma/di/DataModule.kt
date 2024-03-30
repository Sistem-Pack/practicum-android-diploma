package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module
import ru.practicum.android.diploma.app.App

val dataModule = module {

    factory {
        Gson()
    }

    single {
        App()
    }
}
