package ru.practicum.android.diploma.data.dto.areas

import com.google.gson.annotations.SerializedName

data class AreasDto(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val name: String,
    val areas: List<AreasDto>?
)
