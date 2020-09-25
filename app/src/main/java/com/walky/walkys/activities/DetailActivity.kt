package com.walky.walkys.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.grocito.grocito.adapter.ImageViewPagerAdapter
import com.walky.responses.HomeResponse
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.adapters.HomeUserAdapter
import com.walky.walkys.databinding.ActivityDetailBinding
import java.util.*

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    var topAdaper: HomeUserAdapter? = null
    var imageViewPagerAdapter: ImageViewPagerAdapter? = null
    var arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    var array = intArrayOf(
        R.drawable.home_image_1,
        R.drawable.home_image_2,
        R.drawable.home_image_1,
        R.drawable.home_image_2,
        R.drawable.home_image_1
    )
//    var imageModelArrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.statusBarOverride(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.header.titleTv.text = resources.getString(R.string.userDetail)
        binding.header.titleTv.setTextColor(resources.getColor(R.color.white))
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        Utils.tintColor(this@DetailActivity, binding.header.optionMenu, R.color.white)
        Utils.tintColor(this@DetailActivity, binding.header.backIv, R.color.white)
        binding.header.backIv.setOnClickListener { finish() }
        binding.recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.isNestedScrollingEnabled = false
        topAdaper = HomeUserAdapter(this, arrayList)
        binding.recyclerView.adapter = topAdaper

        imageViewPagerAdapter = ImageViewPagerAdapter(this, array)
        binding.viewPager.adapter = imageViewPagerAdapter
        binding.dotsIndicator.setViewPager(binding.viewPager)

    }

}