package ru.practicum.android.diploma.util

class SalaryInfo {

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
            "зарплата не указана"
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
        if (salary.length > 3) {
            for (i in 1..salary.length / 3) {
                val subStr = " " + salary.substring((salary.length - 3 * i), salary.length - 3 * i + 3)
                result.insert(0, subStr)
            }
            result.insert(0, salary.substring(0, salary.length - 3 * (salary.length / 3)))
            return result.toString().trim()
        } else {
            return salary
        }
    }
}






