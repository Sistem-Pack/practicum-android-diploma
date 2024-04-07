package ru.practicum.android.diploma.data.dto.vacancy

/**
 * Data-класс dto для ЗП
 * @param currency - валюта (RUR)
 * @param from - ЗП от (15000)
 * @param to - ЗП по (может null)
 */

data class SalaryDto(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)
