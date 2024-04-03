package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName

/**
 * Data-класс dto для станций метро
 * @param lineName - наименование линии метро
 * @param stationName - наименовании станции метро
 */

data class MetroStationsDto(
    @SerializedName("line_name")
    val lineName: String?,
    @SerializedName("station_name")
    val stationName: String?
)
