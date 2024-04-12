package ru.practicum.android.diploma.ui.main.vacancy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class LoadingItemAdapter : RecyclerView.Adapter<LoadingItemViewHolder>() {

    var visible: Boolean = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        return LoadingItemViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: LoadingItemViewHolder, position: Int) {
        holder.bind(visible)
    }
}
