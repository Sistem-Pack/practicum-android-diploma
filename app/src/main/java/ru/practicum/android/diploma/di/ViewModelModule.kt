package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.main.MainViewModel

val viewModelModule = module {

    viewModel {
        FavoritesViewModel(favoriteVacanciesInteractor = get(), vacancyDetailsInteractor = get(), utils = get())
    }

    viewModel {
        MainViewModel(get(), get())
    }

}
