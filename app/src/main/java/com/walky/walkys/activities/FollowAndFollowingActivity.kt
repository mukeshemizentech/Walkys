package com.walky.walkys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.adapters.ViewPageTabAdapter
import com.walky.walkys.databinding.ActivityFollowAndFollowingBinding
import com.walky.walkys.fragments.FollowersFragment
import com.walky.walkys.fragments.ImageFragment
import com.walky.walkys.fragments.PetLeaderBoardFragment

class FollowAndFollowingActivity : AppCompatActivity() {


    lateinit var binding : ActivityFollowAndFollowingBinding
    var viewPageAdapter : ViewPageTabAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_follow_and_following)

        binding.header.titleTv.text = getString(R.string.follower_and_following)
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { finish() }
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.setOnClickListener {
            startActivity(
                Intent(
                    this@FollowAndFollowingActivity,
                    NewPostActivity::class.java
                )
            )
        }
        viewPageAdapter = ViewPageTabAdapter(supportFragmentManager)
        viewPageAdapter!!.addFragment(FollowersFragment(), resources.getString(R.string.followers))
        viewPageAdapter!!.addFragment(FollowersFragment(), resources.getString(R.string.following))



        binding.htabViewpager.adapter = viewPageAdapter
        binding.userProfileTabLayout.setupWithViewPager(binding.htabViewpager)

        binding.htabViewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.userProfileTabLayout
            )
        )
    }
}