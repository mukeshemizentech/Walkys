package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.walkys.R
import com.walky.walkys.databinding.SearchItemBinding

class SearchAdapter(var mContext: Context) :
    RecyclerView.Adapter<SearchAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: SearchItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.search_item,
            parent,
            false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {}
    override fun getItemCount(): Int {
        return 10
    }

    inner class ItemHolder(itemView: SearchItemBinding) :
        RecyclerView.ViewHolder(itemView.getRoot()) {
        var binding: SearchItemBinding

        init {
            binding = itemView
        }
    }
}