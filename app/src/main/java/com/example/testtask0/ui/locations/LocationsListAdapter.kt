package com.example.testtask0.ui.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask0.R
import com.example.testtask0.data.network.api.response.LocationResponse

class LocationsListAdapter(
    private val onItemSelected: (LocationResponse) -> Unit
) : PagedListAdapter<LocationResponse, LocationsListAdapter.ViewHolder>(ItemDiffCallback()) {

    private val randomImageLinks = listOf(
        "https://i.picsum.photos/id/20/600/600.jpg?hmac=91maC538H_TtZ-ACJhdrP5T_cUKuZsCkSEXkbHLjvLA",
        "https://i.picsum.photos/id/127/600/600.jpg?hmac=km47e-dKPYG-1vheoEz3WlbUrtSrYoeJ7Aft7UYbm_M",
        "https://i.picsum.photos/id/183/600/600.jpg?hmac=JRIkl4m2tWTzD6w5jHm9l0ayN4lJE4BhUnxf8J_cj0A",
        "https://i.picsum.photos/id/989/600/600.jpg?hmac=y2H3p_6IUICrOB52uoOVsgWYL5lsMZaaS4mQoZ-y4oQ",
        "https://i.picsum.photos/id/11/600/600.jpg?hmac=OoD3fF4hlAiZhgA6M9z_7Db_Mrhp4TKDfsWgx_7mYbI",
        "https://i.picsum.photos/id/845/600/600.jpg?hmac=csTkHrpkkzsOsMb0aLWWMBKNczp6_PHK50VPBFb_p84",
        "https://i.picsum.photos/id/1006/600/600.jpg?hmac=BKGVDYxDU4NAKE6eca7lrsiGWzkS2ejOhDGEO8S_iO8",
        "https://i.picsum.photos/id/723/600/600.jpg?hmac=SZz9YeQOA-Y2gnBBw9nKsEOs0aKeBk598PPHdvYlLLA",
        "https://i.picsum.photos/id/958/600/600.jpg?hmac=6aQCup0gPCKR0qMf5SiYoq33H1S9bjrgDM4CWc7YhEs",
        "https://i.picsum.photos/id/388/600/600.jpg?hmac=4hCtk6YpSVUOhlviMYbdq3Z3aFcPvRzg2mSp5OQ2I30"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_location, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), randomImageLinks[position % randomImageLinks.size], onItemSelected)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView
            get() = itemView.findViewById(R.id.title)
        private val image: ImageView
            get() = itemView.findViewById(R.id.image)

        fun bind(response: LocationResponse?, imageUrl: String, onItemSelected: (LocationResponse) -> Unit) {
            image.load(imageUrl)
            response ?: return
            name.text = response.name
            itemView.setOnClickListener { onItemSelected(response) }
        }

        private fun ImageView.load(imageUrl: String) {
            Glide.with(context).load(imageUrl).centerCrop().into(this)
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<LocationResponse>() {
        override fun areItemsTheSame(oldItem: LocationResponse, newItem: LocationResponse) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: LocationResponse, newItem: LocationResponse) = oldItem == newItem
    }
}
