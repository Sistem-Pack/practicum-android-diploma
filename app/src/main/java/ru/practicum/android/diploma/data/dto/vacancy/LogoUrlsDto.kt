package ru.practicum.android.diploma.data.dto.vacancy

import com.google.gson.annotations.SerializedName

/**
 * Data-класс dto для лого компаний
 * @param smallLogoUrl90 - маленький размер лого 90
 * @param mediumLogoUrl240 - средний размер лого 240
 * @param originalLogoUrl - оригинальный размер лого
 */

data class LogoUrlsDto(
    @SerializedName("90")
    val smallLogoUrl90: String?,
    @SerializedName("240")
    val mediumLogoUrl240: String?,
    @SerializedName("original")
    val originalLogoUrl: String?
)
