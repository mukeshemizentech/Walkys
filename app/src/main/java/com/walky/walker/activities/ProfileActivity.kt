package com.walky.walker.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.walky.walkys.activities.NewPostActivity
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.headerCommon.backIv.setOnClickListener { view -> onBackPressed() }
        binding.headerCommon.titleTv.setText("Profile")
        binding.headerCommon.filterIv.setImageDrawable(resources.getDrawable(R.drawable.add_post))
        binding.headerCommon.filterIv.setImageResource(R.drawable.add_post)
        binding.headerCommon.filterIv.setOnClickListener { view ->
            startActivity(
                Intent(
                    this@ProfileActivity,
                    NewPostActivity::class.java
                )
            )
        }
    }
}