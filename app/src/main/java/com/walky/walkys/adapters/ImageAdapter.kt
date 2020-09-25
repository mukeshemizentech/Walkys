package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.ImageItemBinding
import java.util.*

class ImageAdapter(context: Context, arrayList: ArrayList<HomeResponse>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    var arrayList: ArrayList<HomeResponse>
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ImageItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.image_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {}
    override fun getItemCount(): Int {
        return 50
    }

    inner class ViewHolder(binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ImageItemBinding

        init {
            this.binding = binding
        }
    }

    init {
        this.arrayList = arrayList
        this.context = context
    }
}
