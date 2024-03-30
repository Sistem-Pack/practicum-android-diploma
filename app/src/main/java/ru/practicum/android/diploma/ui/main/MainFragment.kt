package ru.practicum.android.diploma.ui.main

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.presentation.main.MainViewModel

class MainFragment(): Fragment() {

    private val viewModel by viewModel<MainViewModel>()
}
