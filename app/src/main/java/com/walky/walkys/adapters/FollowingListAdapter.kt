package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.FollowingListItemBinding
import java.util.*

class FollowingListAdapter(context: Context, arrayList: ArrayList<HomeResponse>) :
    RecyclerView.Adapter<FollowingListAdapter.ViewHolder>() {
    var arrayList: ArrayList<HomeResponse> = arrayList
    private val context: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FollowingListItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.following_list_item,
                parent,
                false
            )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.followingTv.setOnClickListener {
            if (viewHolder.binding.followingTv.text == context.getString(R.string.follow)) {
                viewHolder.binding.followingTv.text = context.getString(R.string.following)
            } else {
                viewHolder.binding.followingTv.text = context.getString(R.string.follow)
            }
        }

    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(binding: FollowingListItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: FollowingListItemBinding = binding

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
