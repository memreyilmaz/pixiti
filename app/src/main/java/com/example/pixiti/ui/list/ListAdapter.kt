package com.example.pixiti.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pixiti.databinding.ItemListBinding
import com.example.pixiti.model.Image
import com.example.pixiti.utils.loadImage

typealias ListItemClickListener = (Image?) -> Unit

class ListAdapter : PagingDataAdapter<Image, ListAdapter.ListViewHolder>(IMAGE_COMPARATOR) {

    var onItemClickListener: ListItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAdapter.ListViewHolder {
        val binding = LayoutInflater.from(parent.context).let {
            ItemListBinding.inflate(it, parent, false)
        }
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        val imageItem = getItem(position)
        if (imageItem != null) {
            holder.bind(imageItem)
        }
    }

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            binding.apply {
                imageViewListName.loadImage(imageUrl = item.previewURL, context = itemView.context)
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem == newItem
        }
    }
}