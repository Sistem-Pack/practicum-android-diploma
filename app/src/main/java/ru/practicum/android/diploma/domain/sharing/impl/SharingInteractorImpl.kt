package ru.practicum.android.diploma.domain.sharing.impl

import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.SharingRepository

class SharingInteractorImpl(
    private val repository: SharingRepository
) : SharingInteractor {
    override fun shareLink(link: String) {
        repository.shareLink(link)
    }

    override fun sendEmail(email: String) {
        repository.sendEmail(email)
    }

    override fun makeACall(phoneNumber: String) {
        repository.makeACall(phoneNumber)
    }
}
