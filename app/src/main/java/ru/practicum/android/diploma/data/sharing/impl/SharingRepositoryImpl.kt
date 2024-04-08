package ru.practicum.android.diploma.data.sharing.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.domain.sharing.SharingRepository

class SharingRepositoryImpl(
    private val context: Context,
    val app: App
) : SharingRepository {
    override fun shareLink(link: String) {
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            app.startActivity(this)
        }
    }

    override fun sendEmail(email: String) {
        Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, email)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            app.startActivity(Intent.createChooser(this, "Направить"))
        }
    }

    override fun makeACall(phoneNumber: String) {
        Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            app.startActivity(this)
        }
    }
}

