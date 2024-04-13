package ru.practicum.android.diploma.data.details.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsResponse
import ru.practicum.android.diploma.data.dto.vacancy.KeySkillsDto
import ru.practicum.android.diploma.data.dto.vacancy.PhonesDto
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.ResponseStatus
import ru.practicum.android.diploma.domain.models.VacancyDetailsResult
import ru.practicum.android.diploma.domain.models.vacancy.VacancyDetails
import ru.practicum.android.diploma.util.SalaryInfo

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val utils: SalaryInfo
) : VacancyDetailsRepository {

    override fun detailsVacancy(id: String): Flow<VacancyDetailsResult> = flow {
        val response = networkClient.doVacancyDetailsSearch(VacancyDetailsRequest(id))
        when (response.resultResponse) {
            ResponseStatus.OK -> {
                val responseVacancy: VacancyDetails
                (response as VacancyDetailsResponse).let {
                    responseVacancy = formatToVacancyDetails(it)
                    Log.d("Network", "response $it")
                }
                emit(
                    VacancyDetailsResult(responseVacancy, ResponseStatus.OK, response.resultCode)
                )
            }

            ResponseStatus.NO_CONNECTION -> {
                emit(
                    VacancyDetailsResult(null, ResponseStatus.NO_CONNECTION, response.resultCode)
                )
            }

            ResponseStatus.BAD -> {
                emit(VacancyDetailsResult(null, ResponseStatus.BAD, response.resultCode))
            }

            else -> emit(
                VacancyDetailsResult(null, ResponseStatus.DEFAULT, response.resultCode)
            )
        }
    }

    private fun formatToVacancyDetails(vacancyDetailsResponse: VacancyDetailsResponse): VacancyDetails {
        return VacancyDetails(
            vacancyId = vacancyDetailsResponse.id,
            vacancyName = vacancyDetailsResponse.name.toString(),
            employer = vacancyDetailsResponse.employer?.name.toString(),
            industry = vacancyDetailsResponse.industry?.name.toString(),
            country = vacancyDetailsResponse.country?.name.toString(),
            areaId = vacancyDetailsResponse.address?.full.toString(),
            areaRegion = vacancyDetailsResponse.area?.name.toString(),
            contactsEmail = vacancyDetailsResponse.contacts?.email.toString(),
            contactsName = vacancyDetailsResponse.contacts?.name.toString(),
            contactsPhones = phonesMap(vacancyDetailsResponse.contacts?.phones),
            description = vacancyDetailsResponse.description.toString(),
            employmentType = vacancyDetailsResponse.employment?.name.toString(),
            experienceName = vacancyDetailsResponse.experience?.name.toString(),
            salary = utils.getSalaryInfo(
                vacancyDetailsResponse.salary?.currency ?: "",
                (vacancyDetailsResponse.salary?.from ?: "").toString(),
                (vacancyDetailsResponse.salary?.to ?: "").toString()
            ),
            keySkills = keySkillMap(vacancyDetailsResponse.keySkills),
            artworkUrl = vacancyDetailsResponse.employer?.logoUrls?.smallLogoUrl90 ?: ""
        )
    }

    private fun phonesMap(phones: List<PhonesDto>?): String {
        var phonesList = ""
        if (phones != null) {
            for (i in phones) {
                phonesList += "+${i.country}${i.city}${i.number}^comment=${i.comment.toString().replace("null", "")};"
            }
        }
        return phonesList
    }

    private fun keySkillMap(keySkills: List<KeySkillsDto>?): String {
        var keySkillsToString = ""
        if (keySkills != null) {
            for (i in keySkills) {
                keySkillsToString += "${i.name};"
            }
        }
        return keySkillsToString
    }

}
