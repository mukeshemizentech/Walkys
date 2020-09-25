package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.UserHomeItemBinding

class HomeUserAdapter(var context: Context, arrayList: List<HomeResponse>) :
    RecyclerView.Adapter<HomeUserAdapter.ViewHolder>() {
    var arrayList: List<HomeResponse>
    var array = intArrayOf(
        R.drawable.home_image_1,
        R.drawable.home_image_2,
        R.drawable.home_image_1,
        R.drawable.home_image_2,
        R.drawable.home_image_1
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        animZoomIn = AnimationUtils.loadAnimation(context,R.anim.zoom_in);
        val binding: UserHomeItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_home_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.nameTv.text = String.format("%d.Charls Niclose ",(position+1))

//        ScheduleResponse scheduleResponse = arrayList.get(position);

//        holder.binding.viewTv.setOnClickListener(v -> context.startActivity(new Intent(context, NewRequestMapsActivity.class)));

//        viewHolder.binding.postImage.setImageResource(array[position]);
//        viewHolder.binding.shareTv.setOnClickListener(view -> {
//            Utils.shareIntent(context, context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.app_name));
//        });
//
//        viewHolder.binding.likeIv.setOnClickListener(view -> {
//            if (isLike) {
//                isLike = false;
//                viewHolder.binding.likeIv.setImageResource(R.drawable.heart);
//            }else {
//                isLike = true;
//                viewHolder.binding.likeIv.setImageResource(R.drawable.heat_fill);
//                viewHolder.binding.likeIv.startAnimation(animZoomIn);
//            }
//        });

//        Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation);
//        viewHolder.itemView.setAnimation(animation);
    }

    override fun getItemCount(): Int {
        return 50
    }

    inner class ViewHolder(itemView: UserHomeItemBinding) :
        RecyclerView.ViewHolder(itemView.getRoot()) {
        var binding: UserHomeItemBinding

        init {
            binding = itemView
        }
    }

    companion object {
        //    Animation animZoomIn;
        private const val isLike = false
    }

    init {
        this.arrayList = arrayList
    }
}
