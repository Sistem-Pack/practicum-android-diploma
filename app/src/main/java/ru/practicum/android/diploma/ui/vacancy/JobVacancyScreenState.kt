package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails

sealed interface JobVacancyScreenState {

    data class VacancyUploaded(val vacancy: VacancyDetails) : JobVacancyScreenState

    data class FailedRequest(val error: String) : JobVacancyScreenState

    object UploadingProcess : JobVacancyScreenState

    object NoConnection : JobVacancyScreenState

}
