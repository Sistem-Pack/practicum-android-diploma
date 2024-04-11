package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.details.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.search.VacancyInteractor
import ru.practicum.android.diploma.domain.search.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.domain.sharedprefs.GetFiltersInteractor
import ru.practicum.android.diploma.domain.sharedprefs.GetFiltersInteractorImpl
import ru.practicum.android.diploma.domain.sharedprefs.PutFiltersInteractor
import ru.practicum.android.diploma.domain.sharedprefs.PutFiltersInteractorImpl
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractorImpl

val interactorModule = module {

    factory<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }

    single<FavoriteVacanciesInteractor> {
        FavoriteVacanciesInteractorImpl(get())
    }

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }

    factory<SharingInteractor> {
        SharingInteractorImpl(get())
    }

    factory<GetFiltersInteractor> {
        GetFiltersInteractorImpl(get())
    }

    factory<PutFiltersInteractor> {
        PutFiltersInteractorImpl(get())
    }
}
