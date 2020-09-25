package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.GroupUserListItemBinding
import java.util.*

class GroupUserListAdapter(context: Context, arrayList: ArrayList<HomeResponse>) :
    RecyclerView.Adapter<GroupUserListAdapter.ViewHolder>() {
    var arrayList: ArrayList<HomeResponse> = arrayList
    var array: BooleanArray = booleanArrayOf(
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )
    private val context: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: GroupUserListItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.group_user_list_item,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var employee = array[position]
        viewHolder.itemView.setOnClickListener {
//            if (activity == "mapFragment") {
//                selected_position = position
//                notifyDataSetChanged()
//            } else {
            employee = if (employee) {
                viewHolder.binding.checkAndUncheckIv.setImageResource(R.drawable.unchecked)
                false
            } else {
                viewHolder.binding.checkAndUncheckIv.setImageResource(R.drawable.checked)
                true
            }
        }
//        }
    }

    override fun getItemCount(): Int {
        return array.size
    }

    inner class ViewHolder(binding: GroupUserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: GroupUserListItemBinding = binding

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
