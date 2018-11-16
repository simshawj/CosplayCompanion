package com.jamessimshaw.cosplaycompanion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jamessimshaw.cosplaycompanion.R
import com.jamessimshaw.cosplaycompanion.models.Convention
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_convention.view.*

val diff = object : DiffUtil.ItemCallback<Convention>() {
    override fun areItemsTheSame(oldItem: Convention, newItem: Convention): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Convention, newItem: Convention): Boolean {
        return oldItem == newItem
    }
}

class ConventionAdapter: ListAdapter<Convention, ConventionViewHolder>(diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConventionViewHolder {
        return ConventionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_convention, parent, false))
    }

    override fun onBindViewHolder(holder: ConventionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ConventionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(convention: Convention) {
        itemView.convention_name.text = convention.name
        itemView.conDescriptionTextView.text = convention.description
        Picasso.get()
                .load(convention.logoUriString).fit().centerInside()
                .into(itemView.convention_logo)
    }
}