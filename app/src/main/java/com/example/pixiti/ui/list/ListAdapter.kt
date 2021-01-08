package com.example.pixiti.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pixiti.databinding.ItemListBinding
import com.example.pixiti.model.Image

typealias ListItemClickListener = (Image?) -> Unit

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var images: List<Image> = listOf()
    var onItemClickListener: ListItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAdapter.ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context))
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    inner class ListViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            binding.textViewListName.text = item.tags
            Glide.with(binding.imageViewListName.context)
                .load(item.previewURL)
                .into(binding.imageViewListName)
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    fun updateItems(items: List<Image>) {
        images = items
        notifyDataSetChanged()
    }
}