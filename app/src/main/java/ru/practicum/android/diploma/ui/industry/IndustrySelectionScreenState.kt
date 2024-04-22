package ru.practicum.android.diploma.ui.industry

import ru.practicum.android.diploma.domain.models.industry.Industry

sealed interface IndustrySelectionScreenState {

    data class IndustriesUploaded(
        val industries: ArrayList<Industry>,
        val selectedIndustryId: String,
    ) : IndustrySelectionScreenState

    object FailedRequest : IndustrySelectionScreenState

    object UploadingProcess : IndustrySelectionScreenState

    object NoConnection : IndustrySelectionScreenState

}
