package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.area.impl.AreasRepositoryImpl
import ru.practicum.android.diploma.data.db.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.details.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.industry.impl.IndustryRepositoryImpl
import ru.practicum.android.diploma.data.search.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.data.sharedprefs.FiltersRepositoryImpl
import ru.practicum.android.diploma.data.sharing.impl.SharingRepositoryImpl
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.industry.IndustryRepository
import ru.practicum.android.diploma.domain.search.VacancyRepository
import ru.practicum.android.diploma.domain.sharedprefs.FiltersRepository
import ru.practicum.android.diploma.domain.sharing.SharingRepository

val repositoryModule = module {

    factory<VacancyRepository> {
        VacancyRepositoryImpl(get(), get())
    }

    single<FavoriteVacanciesRepository> {
        FavoriteVacanciesRepositoryImpl(get(), get())
    }

    factory<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get())
    }

    single<SharingRepository> {
        SharingRepositoryImpl(get())
    }

    factory<IndustryRepository> {
        IndustryRepositoryImpl(get(), get())
    }

    factory<AreasRepository> {
        AreasRepositoryImpl(get(), get())
    }

    factory<FiltersRepository> {
        FiltersRepositoryImpl(get(), get())
    }

}
