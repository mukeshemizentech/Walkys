package com.walky.walkys.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.walky.walkys.activities.DetailActivity
import com.walky.utils.Utils
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.activities.ProfileActivity
import com.walky.walkys.databinding.HomeItemBinding

class HomeAdaper(var context: Context, arrayList: List<HomeResponse>) :
    RecyclerView.Adapter<HomeAdaper.ViewHolder>() {
    var arrayList: List<HomeResponse>
    var array = intArrayOf(
        R.drawable.home_image_1,
        R.drawable.home_image_2,
        R.drawable.home_image_1,
        R.drawable.home_image_2,
        R.drawable.home_image_1
    )
    var animZoomIn: Animation? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        animZoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
        val binding: HomeItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.home_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

//        ScheduleResponse scheduleResponse = arrayList.get(position);

//        holder.binding.viewTv.setOnClickListener(v -> context.startActivity(new Intent(context, NewRequestMapsActivity.class)));

//        viewHolder.binding.postImage.setImageResource(array[position]);
        viewHolder.binding.shareTv.setOnClickListener { view ->
            Utils.shareIntent(
                context,
                context.resources.getString(R.string.app_name),
                context.resources.getString(R.string.app_name)
            )
        }
        viewHolder.binding.likeIv.setOnClickListener { view ->
            if (isLike) {
                isLike = false
                viewHolder.binding.likeIv.setImageResource(R.drawable.heart)
            } else {
                isLike = true
                viewHolder.binding.likeIv.setImageResource(R.drawable.heat_fill)
                viewHolder.binding.likeIv.startAnimation(animZoomIn)
            }
        }
        viewHolder.itemView.setOnClickListener {
            context.startActivity(Intent(context, DetailActivity::class.java))
            //                AppCompatActivity activity = (AppCompatActivity)context;
            //                Fragment myFragment = new UserHomeFragment();
            //                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, myFragment).addToBackStack(null).commit();
        }
        viewHolder.binding.roundedImageView.setOnClickListener {
            context.startActivity(Intent(context, ProfileActivity::class.java))
            //                AppCompatActivity activity = (AppCompatActivity)context;
            //                Fragment myFragment = new UserHomeFragment();
            //                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, myFragment).addToBackStack(null).commit();
        }
        viewHolder.binding.threeDotsMenu.setOnClickListener { view ->
            showPopupMenu(
                viewHolder.binding.threeDotsMenu,
                position
            )
        }
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)
        viewHolder.itemView.animation = animation
    }

    private fun showPopupMenu(view: View, position: Int) {
        // inflate menu
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.pop_menu, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener(position))
        popup.show()
    }

    override fun getItemCount(): Int {
        return 500
    }

    inner class ViewHolder(itemView: HomeItemBinding) :
        RecyclerView.ViewHolder(itemView.getRoot()) {
        var binding: HomeItemBinding

        init {
            binding = itemView
        }
    }

    internal inner class MyMenuItemClickListener(private val position: Int) :
        PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.Not_interasted_catugury -> {
                    //                    String RemoveCategory =arrayList.get(position).getCategory();
                    // mDataSet.remove(position);
                    //notifyItemRemoved(position);
                    // notifyItemRangeChanged(position,mDataSet.size());
                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show()
                    return true
                }
                R.id.No_interasted -> {
                    //                    arrayList.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position,arrayList.size());
                    Toast.makeText(context, "Done for now", Toast.LENGTH_SHORT).show()
                    return true
                }
                else -> {
                }
            }
            return false
        }
    }

    companion object {
        private var isLike = false
    }

    init {
        this.arrayList = arrayList
    }
}
