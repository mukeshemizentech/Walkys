package com.walky.walkys.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.activities.PetProfileActivity
import com.walky.walkys.databinding.DogItemBinding
import java.util.*

class DogListAdapter(context: Context, arrayList: ArrayList<HomeResponse>, activity: String) :
    RecyclerView.Adapter<DogListAdapter.ViewHolder>() {
    var arrayList: ArrayList<HomeResponse>
    private val context: Context
    private val activity: String

    var selected_position = -1
    companion object{
        var isSelected: Boolean? = false
    }


    init {
        this.arrayList = arrayList
        this.context = context
        this.activity = activity;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DogItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dog_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.dogNameTv.setText((position + 1).toString() + ".Ben")

        if (activity == "mapFragment") {
            if (selected_position == position) {
                isSelected = true
                viewHolder.binding.selectIv.visibility = View.VISIBLE
            } else {
                viewHolder.binding.selectIv.visibility = View.GONE
            }
        }
        viewHolder.itemView.setOnClickListener {
            if (activity == "mapFragment") {
                selected_position = position
                notifyDataSetChanged()
            } else {
                context.startActivity(
                    Intent(
                        context,
                        PetProfileActivity::class.java
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(binding: DogItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: DogItemBinding

        init {
            this.binding = binding
            if (activity == "mapFragment") {

                binding.dogNameTv.setTextColor(context.resources.getColor(R.color.textColorPrimary))
            } else {
                binding.selectIv.visibility = View.GONE
            }
        }


    }


}
