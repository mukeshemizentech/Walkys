package com.walky.walker.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.walky.responses.HomeResponse
import com.walky.walker.activities.UpComingActivity
import com.walky.walkys.R
import com.walky.walkys.databinding.MyRequestItemBinding

class MyRequestAdaper(var context: Context, arrayList: List<HomeResponse>) :
    RecyclerView.Adapter<MyRequestAdaper.ViewHolder>() {
    var arrayList: List<HomeResponse>
    var array = arrayOf(
        "Tara Suthria",
        "Zara khan",
        "Ketty perry",
        "Suthria",
        "Users",
        "Evanka",
        "Beaches",
        "Ketty perry"
    )
    var imge = intArrayOf(
        R.drawable.home_image_1,
        R.drawable.home_image_2,
        R.drawable.user_demo,
        R.drawable.home_image_1,
        R.drawable.home_image_2,
        R.drawable.user_demo,
        R.drawable.home_image_1,
        R.drawable.home_image_2
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MyRequestItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.my_request_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        ScheduleResponse scheduleResponse = arrayList.get(position);

//        holder.binding.viewTv.setOnClickListener(v -> context.startActivity(new Intent(context, NewRequestMapsActivity.class)));
        holder.binding.nameTv.setText(array[position])
        Glide.with(context).load(imge[position]).circleCrop().into(holder.binding.userPic)
        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(
                    context,
                    UpComingActivity::class.java
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return array.size
    }

    class ViewHolder(itemView: MyRequestItemBinding) :
        RecyclerView.ViewHolder(itemView.getRoot()) {
        var binding: MyRequestItemBinding

        init {
            binding = itemView
        }
    }

    init {
        this.arrayList = arrayList
    }
}
