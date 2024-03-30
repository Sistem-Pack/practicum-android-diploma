package ru.practicum.android.diploma.ui.filtering_settings

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.filtering_settings.FilteringSettingsViewModel

class FilteringSettingsFragment(): Fragment() {

    private val viewModel by viewModel<FilteringSettingsViewModel>()
}
