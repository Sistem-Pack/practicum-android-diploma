package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.details.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.search.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.data.sharedprefs.GetFiltersRepositoryImpl
import ru.practicum.android.diploma.data.sharedprefs.PutFiltersRepositoryImpl
import ru.practicum.android.diploma.data.sharing.impl.SharingRepositoryImpl
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.search.VacancyRepository
import ru.practicum.android.diploma.domain.sharedprefs.GetFiltersRepository
import ru.practicum.android.diploma.domain.sharedprefs.PutFiltersRepository
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

    factory<SharingRepository> {
        SharingRepositoryImpl(get())
    }

    factory<GetFiltersRepository> {
        GetFiltersRepositoryImpl(get())
    }

    factory<PutFiltersRepository> {
        PutFiltersRepositoryImpl(get())
    }

}
