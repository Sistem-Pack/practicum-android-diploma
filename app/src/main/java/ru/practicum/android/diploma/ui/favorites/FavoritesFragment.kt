package ru.practicum.android.diploma.ui.favorites

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel

class FavoritesFragment(): Fragment() {

    private val viewModel by viewModel<FavoritesViewModel>()
}
