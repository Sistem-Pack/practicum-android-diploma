package ru.practicum.android.diploma.data.dto.vacancy

/**
 * Data-класс dto со списком номеров телефонов к вакансии
 * @param city - код города
 * @param comment - Комментарий (удобное время для звонка по этому номеру)
 * @param country - Код страны
 * @param formatted - Телефонный номер
 * @param number - Телефон
 */

data class PhonesDto(
    val city: String,
    val comment: String? = null,
    val country: String,
    val formatted: String,
    val number: String
)
