package ru.practicum.android.diploma.ui.main.vacancy

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class LoadingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val progressBar: ProgressBar = itemView.findViewById(R.id.pbLoading)
    fun bind(visible: Boolean) {
        progressBar.isVisible = visible
    }
}
