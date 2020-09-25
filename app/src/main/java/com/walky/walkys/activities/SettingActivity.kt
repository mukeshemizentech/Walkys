package com.walky.walkys.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        binding.header.titleTv.text = resources.getString(R.string.setting)
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { finish() }
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.setOnClickListener {
            startActivity(
                Intent(
                    this@SettingActivity,
                    NewPostActivity::class.java
                )
            )
        }
    }
}