package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filter.FilteringSettingsViewModel
import ru.practicum.android.diploma.presentation.industry.IndustrySelectionViewModel
import ru.practicum.android.diploma.presentation.main.MainViewModel
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
        MainViewModel(
            vacancyInteractor = get(),
            utilities = get(),
            filtersInteractorImpl = get()
        )
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
        IndustrySelectionViewModel(
            filtersInteractor = get(),
            industryInteractor = get(),
        )
    }

    viewModel {
        FilteringSettingsViewModel(
            filtersInteractorImpl = get()
        )
    }

}
