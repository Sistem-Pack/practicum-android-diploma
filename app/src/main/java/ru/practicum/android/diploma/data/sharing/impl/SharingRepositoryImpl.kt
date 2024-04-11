package ru.practicum.android.diploma.data.sharing.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.sharing.SharingRepository

class SharingRepositoryImpl(
    private val context: Context,
) : SharingRepository {

    override fun shareLink(link: String) {
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, context.getString(R.string.url) + link)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }
    }

    override fun sendEmail(email: String) {
        Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }
    }

    override fun makeACall(phoneNumber: String) {
        Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }
    }
}
