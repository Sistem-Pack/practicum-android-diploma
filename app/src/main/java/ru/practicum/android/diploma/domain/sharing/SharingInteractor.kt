package ru.practicum.android.diploma.domain.sharing

interface SharingInteractor {
    fun shareLink(link: String)
    fun sendEmail(email: String)
    fun makeACall(phoneNumber: String)
}
