package ru.practicum.android.diploma.data.dto.vacancy

/**
 * Data-класс dto со списком контактов
 * @param id - id страны
 * @param name - наименование страны
 * @param phones - телефон
 * @see PhonesDto
 */

data class ContactsDto(
    val email: String?,
    val name: String?,
    val phones: List<PhonesDto>? = null
)
