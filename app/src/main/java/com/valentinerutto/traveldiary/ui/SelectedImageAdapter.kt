package com.valentinerutto.traveldiary.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.valentinerutto.traveldiary.data.model.SelectedImages
import com.valentinerutto.traveldiary.databinding.RowImagesBinding

class SelectedImageAdapter : ListAdapter<SelectedImages, SelectedImageAdapter.ImageViewHolder>(
    diff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)
    }

    class ImageViewHolder(private val binding: RowImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: SelectedImages) {
            binding.img.load(Uri.parse(image.uri.toString())) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

    }

    companion object {

        fun from(parent: ViewGroup): ImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RowImagesBinding.inflate(
                layoutInflater, parent, false
            )
            return ImageViewHolder(binding)
        }

        val diff = object : DiffUtil.ItemCallback<SelectedImages>() {
            override fun areItemsTheSame(
                oldItem: SelectedImages, newItem: SelectedImages
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SelectedImages, newItem: SelectedImages
            ): Boolean = oldItem == newItem
        }
    }


}