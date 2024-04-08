package ru.practicum.android.diploma.data.details.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsDto
import ru.practicum.android.diploma.data.dto.details.VacancyDetailsResponse
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
        val response = networkClient.doVacancyDetailsSearch(id)
        when (response.resultResponse) {
            ResponseStatus.OK -> {
                val responseVacancy: VacancyDetails =
                    formatToVacancyDetails((response as VacancyDetailsResponse).vacancy)
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

    private fun formatToVacancyDetails(vacancyDetailsDto: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            vacancyId = vacancyDetailsDto.id,
            vacancyName = vacancyDetailsDto.name.toString(),
            employer = vacancyDetailsDto.employer?.name.toString(),
            industry = vacancyDetailsDto.industry?.name.toString(),
            country = vacancyDetailsDto.country?.name.toString(),
            areaId = vacancyDetailsDto.area?.id.toString(),
            areaRegion = vacancyDetailsDto.area?.name.toString(),
            contactsEmail = vacancyDetailsDto.contacts?.email.toString(),
            contactsName = vacancyDetailsDto.contacts?.name.toString(),
            contactsPhones = phonesMap(vacancyDetailsDto.contacts?.phones),
            description = vacancyDetailsDto.description.toString(),
            employmentType = vacancyDetailsDto.employment?.name.toString(),
            experienceName = vacancyDetailsDto.experience?.name.toString(),
            salary = utils.getSalaryInfo(
                vacancyDetailsDto.salary?.currency ?: "",
                (vacancyDetailsDto.salary?.from ?: "").toString(),
                (vacancyDetailsDto.salary?.to ?: "").toString()
            ),
            keySkills = vacancyDetailsDto.keySkills?.name.toString(),
            artworkUrl = vacancyDetailsDto.employer?.logoUrls?.smallLogoUrl90 ?: ""
        )
    }

    private fun phonesMap(phones: List<PhonesDto>?): String {
        var phonesList = ""
        if (phones != null) {
            for (i in phones) {
                phonesList += "+${i.country}${i.city}${i.number}\n"
            }
        }
        return phonesList
    }

}
