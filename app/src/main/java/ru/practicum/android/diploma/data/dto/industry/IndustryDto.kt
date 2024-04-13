package ru.practicum.android.diploma.data.dto.industry

/**
 * Data-класс dto Возвращает двухуровневый справочник всех отраслей
 * @param id - id
 * @param name - наименование отрасли
 * @param name - список отраслей
 */

data class IndustryDto(
    val id: String,
    val name: String,
    val industries: List<IndustryNestedDto>?,
)
