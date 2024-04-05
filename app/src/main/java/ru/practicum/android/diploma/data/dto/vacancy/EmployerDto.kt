package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName

/**
 * Data-класс dto для работодателя
 * @param id - id работодателя
 * @param logoUrls - data-класс для лого
 * @see LogoUrlsDto
 * @param name - наименование работодателя
 * @param url - ссылка на работодателя
 */

data class EmployerDto(
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto?,
    val name: String,
    val url: String?
)
