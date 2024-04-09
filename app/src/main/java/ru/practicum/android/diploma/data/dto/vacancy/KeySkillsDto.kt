package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName

/**
 * Data-класс dto для списка ключевых навыков
 * @param name - список ключевых навыков
 */

data class KeySkillsDto(
    @SerializedName("name")
    val name: String
)
