package com.example.testtask0.ui.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask0.R
import com.example.testtask0.ui.extension.load
import com.example.testtask0.ui.model.Location

class LocationsListAdapter(
    private val onItemSelected: (Location) -> Unit
) : PagedListAdapter<Location, LocationsListAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_location, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemSelected)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView
            get() = itemView.findViewById(R.id.title)
        private val image: ImageView
            get() = itemView.findViewById(R.id.image)

        fun bind(response: Location?, onItemSelected: (Location) -> Unit) {
            response ?: return
            image.load(response.imageUrl)
            name.text = response.name
            itemView.setOnClickListener { onItemSelected(response) }
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Location, newItem: Location) = oldItem == newItem
    }
}
