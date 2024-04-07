package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.search.VacancyInteractor
import ru.practicum.android.diploma.domain.search.impl.VacancyInteractorImpl

val interactorModule = module {
    factory<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }
    single<FavoriteVacanciesInteractor> {
        FavoriteVacanciesInteractorImpl(get())
    }
}
