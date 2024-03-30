package ru.practicum.android.diploma.ui.select_country

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.select_country.SelectCountryViewModel

class SelectCountryFragment(): Fragment() {

    private val viewModel by viewModel<SelectCountryViewModel>()
}
