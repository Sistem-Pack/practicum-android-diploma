package ru.practicum.android.diploma.data.dto.details

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.vacancy.AddressDto
import ru.practicum.android.diploma.data.dto.vacancy.AreaDto
import ru.practicum.android.diploma.data.dto.vacancy.ContactsDto
import ru.practicum.android.diploma.data.dto.vacancy.CountryDto
import ru.practicum.android.diploma.data.dto.vacancy.EmployerDto
import ru.practicum.android.diploma.data.dto.vacancy.EmploymentDto
import ru.practicum.android.diploma.data.dto.vacancy.ExperienceDto
import ru.practicum.android.diploma.data.dto.industry.IndustryDto
import ru.practicum.android.diploma.data.dto.vacancy.KeySkillsDto
import ru.practicum.android.diploma.data.dto.vacancy.SalaryDto

class VacancyDetailsResponse(
    val id: String,
    val name: String?,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val address: AddressDto?,
    val area: AreaDto?,
    val industry: IndustryDto?,
    val country: CountryDto?,
    val contacts: ContactsDto?,
    val description: String?,
    val employment: EmploymentDto?,
    val experience: ExperienceDto?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillsDto>,
) : Response()
