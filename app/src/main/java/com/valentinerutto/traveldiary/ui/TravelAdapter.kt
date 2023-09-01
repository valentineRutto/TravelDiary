package com.valentinerutto.traveldiary.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import com.valentinerutto.traveldiary.databinding.ItemTravelEntryRowBinding


interface OnTravelEntryClicked {
    fun showTravelDetails(travel: TravelDetailsEntity)
}

class TravelAdapter(private var itemClickListener: OnTravelEntryClicked) :
    ListAdapter<TravelDetailsEntity, TravelAdapter.TravelViewHolder>(
        diff
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album, itemClickListener)
    }

    class TravelViewHolder(private val binding: ItemTravelEntryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(travel: TravelDetailsEntity, itemClickListener: OnTravelEntryClicked) {

            binding.txtTitle.text = travel.title
            binding.txtLocation.text = travel.location
            binding.img.load(travel.photo)
            itemView.setOnClickListener {
                itemClickListener.showTravelDetails(travel)
            }
        }

    }

    companion object {

        fun from(parent: ViewGroup): TravelViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTravelEntryRowBinding.inflate(
                layoutInflater, parent, false
            )
            return TravelViewHolder(binding)
        }

        val diff = object : DiffUtil.ItemCallback<TravelDetailsEntity>() {
            override fun areItemsTheSame(
                oldItem: TravelDetailsEntity, newItem: TravelDetailsEntity
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: TravelDetailsEntity, newItem: TravelDetailsEntity
            ): Boolean = oldItem.id == newItem.id
        }
    }


}