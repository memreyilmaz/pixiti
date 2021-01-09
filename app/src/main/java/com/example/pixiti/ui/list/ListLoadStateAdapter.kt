package com.example.pixiti.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pixiti.databinding.LayoutListStateFooterBinding

typealias ListFooterRetryClickListener = () -> Unit

class ListLoadStateAdapter : LoadStateAdapter<ListLoadStateAdapter.ListLoadStateViewHolder>() {

    var onRetrylickListener: ListFooterRetryClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ListLoadStateAdapter.ListLoadStateViewHolder {
        val binding = LayoutListStateFooterBinding.inflate(LayoutInflater.from(parent.context))
        return ListLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ListLoadStateViewHolder(private val binding: LayoutListStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBarListFooter.isVisible = loadState is LoadState.Loading
                textViewListFooterError.isVisible = loadState !is LoadState.Loading

                buttonListRetry.apply {
                    isVisible = loadState !is LoadState.Loading
                    setOnClickListener {
                        onRetrylickListener?.invoke()
                    }
                }
            }
        }
    }
}