package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel

val viewModelModule = module {

    viewModel {
        FavoritesViewModel(favoriteVacanciesInteractor = get())
    }

}
