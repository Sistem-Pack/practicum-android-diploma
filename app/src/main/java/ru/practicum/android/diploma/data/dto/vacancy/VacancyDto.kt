package ru.practicum.android.diploma.data.dto.vacancy

/**
 * Data-класс dto для результата выполнения поискового запроса вакансий на экране поиска вакансий
 * @param id - id вакансии
 * @param name - наименование вакансии вакансии
 * @param salary - зарплата
 * @param employer - работодатель
 * @param address - адресс
 * @see SalaryDto
 * @see EmployerDto
 * @see AddressDto
 */

data class VacancyDto(
    val id: String,
    val name: String?,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val address: AddressDto?,
    val area: AreaDto?
)
