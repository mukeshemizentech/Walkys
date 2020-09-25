package com.grocito.grocito.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.walky.walkys.R
import com.walky.walkys.databinding.ImageViewItemBinding

import java.util.*

class ImageViewPagerAdapter(private val context: Context, imageArray: IntArray) :
    PagerAdapter() {
    var imageArray:IntArray

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ImageViewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.image_view_item, container, false
        )

        binding.imageView.setImageResource(imageArray[position])
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageArray.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    init {
        this.imageArray = imageArray
    }
}