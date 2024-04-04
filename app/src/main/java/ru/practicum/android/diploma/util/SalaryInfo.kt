package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R

class SalaryInfo(private val context: Context) {

    private val mapOfCurrency = mutableMapOf(
        "RUR" to " ₽", // Российский рубль (RUR / RUB)
        "RUB" to " ₽",
        "BYR" to " Br", // Белорусский рубль (BYR)
        "USD" to " $", // Доллар (USD)
        "EUR" to " €", // Евро (EUR)
        "KZT" to " ₸", // Казахстанский тенге (KZT)
        "UAH" to " ₴", // Украинская гривна (UAH)
        "AZN" to " ₼", // Азербайджанский манат (AZN)
        "UZS" to " so’m", // Узбекский сум (UZS)
        "GEL" to " ₾", // Грузинский лари (GEL)
        "KGT" to " с" // Киргизский сом (KGT)
    )

    fun getSalaryInfo(salaryCurrency: String?, salaryFrom: String?, salaryTo: String?): String {
        val salary = if (salaryTo.isNullOrEmpty() && salaryFrom.isNullOrEmpty()) {
            context.getString(R.string.salary_not_specified)
        } else if (salaryTo.isNullOrEmpty()) {
            "от ${splitIntoThousands(salaryFrom!!)}"
        } else if (salaryFrom.isNullOrEmpty()) {
            "до ${splitIntoThousands(salaryTo)}"
        } else {
            "от ${splitIntoThousands(salaryFrom)} до ${splitIntoThousands(salaryTo)}"
        }
        return salary + mapOfCurrency[salaryCurrency]
    }

    private fun splitIntoThousands(salary: String): String {
        val result: StringBuilder = StringBuilder()
        if (salary.length > THREE) {
            for (i in 1..salary.length / THREE) {
                val subStr = " " + salary.substring(salary.length - THREE * i, salary.length - THREE * i + THREE)
                result.insert(0, subStr)
            }
            result.insert(0, salary.substring(0, salary.length - THREE * (salary.length / THREE)))
            return result.toString().trim()
        } else {
            return salary
        }
    }

    companion object {
        const val THREE = 3
    }
}
