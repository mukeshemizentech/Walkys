package com.walky.walkys.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.adapters.ViewPageTabAdapter
import com.walky.walkys.databinding.ActivityPetProfileBinding
import com.walky.walkys.fragments.ImageFragment
import com.walky.walkys.fragments.PetLeaderBoardFragment

class PetProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityPetProfileBinding
    lateinit var viewPageTabAdapter: ViewPageTabAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pet_profile)
        binding.header.titleTv.text = "Stark"
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { view -> finish() }
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.setOnClickListener { view ->
            startActivity(
                Intent(
                    this@PetProfileActivity,
                    NewPostActivity::class.java
                )
            )
        }
//        val params = binding.progressBarIv!!.layoutParams as PercentFrameLayout.LayoutParams
//        val info = params.percentLayoutInfo
//        info.widthPercent = 0.60f
//        info.heightPercent = 1.0f
////        binding.progressBarIv.requestLayout();
//        binding.progressBarIv.layoutParams = params;
        viewPageTabAdapter = ViewPageTabAdapter(supportFragmentManager)
//        userProfileTabAdapter.addFragment(AdventureFragment(), "Adventure")
        viewPageTabAdapter.addFragment(ImageFragment(), "Images")
        viewPageTabAdapter.addFragment(PetLeaderBoardFragment(), "LeaderBoard")


        binding.htabAppbar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, offset ->
            if (offset == 0) {
                // Fully expanded
            } else {
                // Not fully expanded or collapsed
            }
        })

        binding.htabViewpager.adapter = viewPageTabAdapter
        binding.userProfileTabLayout.setupWithViewPager(binding.htabViewpager)

        binding.htabViewpager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.userProfileTabLayout))
    }

}