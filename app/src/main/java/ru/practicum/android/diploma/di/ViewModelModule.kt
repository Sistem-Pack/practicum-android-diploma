package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.presentation.vacancy.JobVacancyViewModel

val viewModelModule = module {

    viewModel {
        FavoritesViewModel(favoriteVacanciesInteractor = get())
    }

    viewModel {
        MainViewModel(get(), get())
    }

    viewModel {
        JobVacancyViewModel(vacancyDetailsInteractor = get(), favoriteVacanciesInteractor = get())
    }

}
