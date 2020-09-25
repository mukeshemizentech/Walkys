package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.LeaderboardItemBinding
import java.util.*

class LeaderBoardAdapter(context: Context, arrayList: ArrayList<HomeResponse>) :
    RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {
    private val arrayList: ArrayList<HomeResponse>
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LeaderboardItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.leaderboard_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}
    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(binding: LeaderboardItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: LeaderboardItemBinding

        init {
            this.binding = binding
        }
    }

    init {
        this.arrayList = arrayList
        this.context = context
    }
}
