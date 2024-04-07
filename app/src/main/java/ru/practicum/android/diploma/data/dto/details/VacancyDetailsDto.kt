package ru.practicum.android.diploma.data.dto.details

import ru.practicum.android.diploma.data.dto.vacancy.AddressDto
import ru.practicum.android.diploma.data.dto.vacancy.AreaDto
import ru.practicum.android.diploma.data.dto.vacancy.ContactsDto
import ru.practicum.android.diploma.data.dto.vacancy.CountryDto
import ru.practicum.android.diploma.data.dto.vacancy.EmployerDto
import ru.practicum.android.diploma.data.dto.vacancy.EmploymentDto
import ru.practicum.android.diploma.data.dto.vacancy.ExperienceDto
import ru.practicum.android.diploma.data.dto.vacancy.IndustryDto
import ru.practicum.android.diploma.data.dto.vacancy.KeySkillsDto
import ru.practicum.android.diploma.data.dto.vacancy.SalaryDto

/**
 * Data-класс dto для результата выполнения поискового запроса вакансий на экране поиска вакансий
 * @param id - id вакансии
 * @param name - наименование вакансии
 * @see SalaryDto
 * @see EmployerDto
 * @see AddressDto
 * @see IndustryDto
 * @see CountryDto
 * @see ContactsDto
 * @see description - описание вакансии
 * @see EmploymentDto
 */

data class VacancyDetailsDto(
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
    val keySkills: KeySkillsDto?
)
