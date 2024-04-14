package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.country.SelectCountryViewModel
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.industry.IndustrySelectionViewModel
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.presentation.place.PlacesOfWorkViewModel
import ru.practicum.android.diploma.presentation.region.RegionSelectionViewModel
import ru.practicum.android.diploma.presentation.vacancy.JobVacancyViewModel

val viewModelModule = module {

    viewModel {
        FavoritesViewModel(
            favoriteVacanciesInteractor = get(),
            vacancyDetailsInteractor = get(),
            utils = get()
        )
    }

    viewModel {
        MainViewModel(get(), get())
    }

    viewModel {
        JobVacancyViewModel(
            favoriteVacanciesInteractor = get(),
            vacancyDetailsInteractor = get(),
            sharingInteractor = get(),
            utils = get()
        )
    }

    viewModel {
        SelectCountryViewModel(
            areasInteractor = get(),
            filtersInteractor = get()
        )
    }

    viewModel {
        PlacesOfWorkViewModel(
            filtersInteractor = get()
        )
    }

    viewModel {
        RegionSelectionViewModel(
            regionInteractor = get(),
            filtersInteractor = get(),
            utilities = get()
        )
    }

    viewModel {
        IndustrySelectionViewModel(
            filtersInteractor = get(),
            industryInteractor = get(),
        )
    }

}
