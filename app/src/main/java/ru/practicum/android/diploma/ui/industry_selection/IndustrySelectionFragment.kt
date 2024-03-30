package ru.practicum.android.diploma.ui.industry_selection

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.industry_selection.IndustrySelectionViewModel

class IndustrySelectionFragment(): Fragment() {

    private val viewModel by viewModel<IndustrySelectionViewModel>()
}
