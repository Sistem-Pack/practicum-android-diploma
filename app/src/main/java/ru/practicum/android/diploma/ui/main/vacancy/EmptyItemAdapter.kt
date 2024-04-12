package ru.practicum.android.diploma.ui.main.vacancy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class EmptyItemAdapter : RecyclerView.Adapter<EmptyItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
        return EmptyItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: EmptyItemViewHolder, position: Int) {
        return
    }
}
