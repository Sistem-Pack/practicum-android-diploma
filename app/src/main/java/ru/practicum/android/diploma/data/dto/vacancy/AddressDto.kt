package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName

/**
 * Data-класс dto для адресов компании
 * @param building - строение, дом (9с10)
 * @param city - город вакансии (Москва и т.п.)
 * @param description - описание места (На проходной нужен пропуск)
 * @param metro - List MetroDto - список станций метро
 * @param street - название улицы
 * @see MetroStationsDto
 */

data class AddressDto(
    val building: String?,
    val city: String?,
    val description: String?,
    @SerializedName("metro_stations")
    val metro: List<MetroStationsDto>?,
    val street: String?,
    @SerializedName("raw")
    val full: String?
)
