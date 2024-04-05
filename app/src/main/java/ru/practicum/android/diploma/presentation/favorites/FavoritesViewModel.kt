package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.db.FavoriteVacanciesInteractor

class FavoritesViewModel(private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor) : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData
}
