package com.example.wallpaper.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaper.databinding.ImageListBinding
import com.example.wallpaper.model.ImageModel

class MainRecyclerAdapter(private val itemClickListener: ItemClickListener) :
    PagedListAdapter<ImageModel, MainRecyclerAdapter.ViewHolder>(diffCallback) {

    interface ItemClickListener{
        fun onClick(position: Int,imageModel: ImageModel,sharedImageView: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ImageListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition)!!)
    }

    inner class ViewHolder(private val binding: ImageListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageModel: ImageModel) {
            with(binding) {
              Glide.with(root.context)
                    .asBitmap()
                    .load(imageModel.urls.small)
                    .into(imageView)
            }

            itemView.setOnClickListener {
                itemClickListener.onClick(adapterPosition,imageModel,binding.imageView)
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ImageModel>() {
            override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}