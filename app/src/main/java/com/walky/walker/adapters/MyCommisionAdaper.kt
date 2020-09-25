package com.walky.walker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.MyCommisionItemBinding

class MyCommisionAdaper(var context: Context, arrayList: List<HomeResponse>) :
    RecyclerView.Adapter<MyCommisionAdaper.ViewHolder>() {
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
        val binding: MyCommisionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.my_commision_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        ScheduleResponse scheduleResponse = arrayList.get(position);

//        holder.binding.viewTv.setOnClickListener(v -> context.startActivity(new Intent(context, NewRequestMapsActivity.class)));
        holder.binding.nameTv.setText(array[position])
        Glide.with(context).load(imge[position]).circleCrop().into(holder.binding.userPic)
        //        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context, UpComingActivity.class));
//            }
//        });
    }

    override fun getItemCount(): Int {
        return array.size
    }

    inner class ViewHolder(itemView: MyCommisionItemBinding) :
        RecyclerView.ViewHolder(itemView.getRoot()) {
        var binding: MyCommisionItemBinding

        init {
            binding = itemView
        }
    }

    init {
        this.arrayList = arrayList
    }
}
