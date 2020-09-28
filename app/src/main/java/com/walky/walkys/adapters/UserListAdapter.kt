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
import com.walky.walkys.ui.chat.ChatActivity
import com.walky.walkys.databinding.ChatItemBinding
import java.util.*

class UserListAdapter(context: Context, arrayList: ArrayList<HomeResponse>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    var arrayList: ArrayList<HomeResponse> = arrayList
    private val context: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ChatItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chat_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if(position==2 || position==4){
            viewHolder.binding.groupMemberIv1.visibility = View.VISIBLE
            viewHolder.binding.groupMemberIv2.visibility = View.VISIBLE
            viewHolder.binding.groupMemberCountTv.visibility = View.VISIBLE
            viewHolder.binding.profileIv.visibility= View.GONE
        }else{
            viewHolder.binding.groupMemberIv1.visibility = View.GONE
            viewHolder.binding.groupMemberIv2.visibility = View.GONE
            viewHolder.binding.groupMemberCountTv.visibility = View.GONE
            viewHolder.binding.profileIv.visibility = View.VISIBLE
        }

        viewHolder.itemView.setOnClickListener {


//            if (activity == "mapFragment") {
//                selected_position = position
//                notifyDataSetChanged()
//            } else {
                context.startActivity(
                    Intent(
                        context,
                        ChatActivity::class.java
                    ).putExtra("position",position.toString())
                )
            }
//        }
    }
    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ChatItemBinding = binding

    }

}
