package ru.practicum.android.diploma.ui.region_selection

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.region_selection.RegionSelectionViewModel

class RegionSelectionFragment(): Fragment() {

    private val viewModel by viewModel<RegionSelectionViewModel>()
}
