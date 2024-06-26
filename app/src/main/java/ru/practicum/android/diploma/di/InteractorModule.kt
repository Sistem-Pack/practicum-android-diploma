package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.impl.AreasInteractorImpl
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.details.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.industry.impl.IndustryInteractorImpl
import ru.practicum.android.diploma.domain.search.VacancyInteractor
import ru.practicum.android.diploma.domain.search.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractor
import ru.practicum.android.diploma.domain.sharedprefs.FiltersInteractorImpl
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

    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }

    factory<IndustryInteractor> {
        IndustryInteractorImpl(get())
    }

    factory<AreasInteractor> {
        AreasInteractorImpl(get())
    }

    factory<FiltersInteractor> {
        FiltersInteractorImpl(get())
    }
}
