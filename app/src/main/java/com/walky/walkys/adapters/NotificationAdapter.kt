package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.NotificationItemBinding

class NotificationAdapter(context: Context, arrayList: ArrayList<HomeResponse>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    private var arrayList : ArrayList<HomeResponse>?=null
    private var context : Context?=null

    init {
        this.arrayList = arrayList
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : NotificationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.notification_item,parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
       return 10
    }
    inner class ViewHolder(binding : NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding : NotificationItemBinding = binding
    }

}