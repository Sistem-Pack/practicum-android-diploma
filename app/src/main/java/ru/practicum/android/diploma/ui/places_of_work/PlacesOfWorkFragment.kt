package ru.practicum.android.diploma.ui.places_of_work

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.places_of_work.PlacesOfWorkViewModel

class PlacesOfWorkFragment(): Fragment() {

    private val viewModel by viewModel<PlacesOfWorkViewModel>()
}
