package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response

class VacancyResponse(
    @SerializedName("items")
    val vacancies: List<VacancyDto>?,
    val found: Int?,
    val page: Int?,
    val pages: Int?
) : Response()
