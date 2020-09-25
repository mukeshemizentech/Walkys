package com.walky.walkys.adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.FollowingListItemBinding

class FollowersAdapter(context :Context, arrayList :ArrayList<HomeResponse>):
        RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var binding : FollowingListItemBinding  = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.following_list_item,parent,false)

                return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return 10
        }

        inner class ViewHolder(binding: FollowingListItemBinding): RecyclerView.ViewHolder(binding.root) {
                var binding : FollowingListItemBinding = binding
        }

}
