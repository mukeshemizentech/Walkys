package com.walky.walkys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.walky.responses.HomeResponse
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.adapters.DogListAdapter
import com.walky.walkys.adapters.ViewPageTabAdapter
import com.walky.walkys.databinding.ActivityProfile2Binding
import com.walky.walkys.fragments.AdventureFragment
import com.walky.walkys.fragments.ImageFragment
import com.walky.walkys.fragments.LeaderBoardFragment
import java.util.ArrayList

class ProfileActivity : AppCompatActivity() {

    private var viewPage_tabAdapter: ViewPageTabAdapter? = null
    private var adapter: DogListAdapter? = null
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    lateinit var binding: ActivityProfile2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile2)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = DogListAdapter(this, arrayList, "myProfileFragment")
        binding.recyclerView.adapter = adapter


        binding.header.titleTv.text = "Jaccia Montaina"
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { view -> finish() }
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.setOnClickListener { view ->
            startActivity(
                Intent(
                    this,
                    NewPostActivity::class.java
                )
            )
        }

        binding.followersCountTv.setOnClickListener {
            startActivity(Intent(this@ProfileActivity,FollowAndFollowingActivity::class.java))
        }
        binding.followingCountTv.setOnClickListener{
            startActivity(Intent(this@ProfileActivity,FollowAndFollowingActivity::class.java))
        }
        addControls()
    }

    private fun addControls() {
        viewPage_tabAdapter = ViewPageTabAdapter(supportFragmentManager)

        viewPage_tabAdapter!!.addFragment(AdventureFragment(), getString(R.string.adventure))
        viewPage_tabAdapter!!.addFragment(ImageFragment(), getString(R.string.images))
        viewPage_tabAdapter!!.addFragment(LeaderBoardFragment(), getString(R.string.leaderboard))
        binding.userProfileViewPager.adapter = viewPage_tabAdapter
        binding.userProfileTabLayout.setupWithViewPager(binding.userProfileViewPager)
        binding.userProfileViewPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.userProfileTabLayout
            )
        )
        binding.userProfileTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.userProfileViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}