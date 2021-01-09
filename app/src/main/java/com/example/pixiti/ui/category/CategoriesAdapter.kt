package com.example.pixiti.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pixiti.databinding.ItemCategoryBinding
import com.example.pixiti.model.Category

typealias CategoryItemClickListener = (String) -> Unit

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categories: List<Category> = listOf()
    var onItemClickListener: CategoryItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.textViewCategoryName.text = itemView.context.resources.getString(item.name)
            Glide.with(binding.imageViewCategoryName.context)
                .load(item.image)
                .into(binding.imageViewCategoryName)
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item.label)
            }
        }
    }

    fun updateItems(items: List<Category>) {
        categories = items
        notifyDataSetChanged()
    }
}