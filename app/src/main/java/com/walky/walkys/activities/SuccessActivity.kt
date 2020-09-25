package com.walky.walkys.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.walky.utils.Utils
import com.walky.utils.drawableBackColor
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivitySuccessBinding

class SuccessActivity : AppCompatActivity() {


   lateinit var binding: ActivitySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils.changeStatusColor(this,R.color.success_back_color)
        Utils.changeStatusTextColor(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_success)
//        binding.header.optionMenu.setImageResource(R.drawable.add_post)
//        binding.header.optionMenu.setVisibility(View.VISIBLE)
//        binding.header.homeMenu.setImageResource(R.drawable.back)
//        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
//        binding.header.homeMenu.setOnClickListener { finish() }
        drawableBackColor(binding.buttonHome, resources.getColor(R.color.colorPrimary))
        binding.buttonHome.setOnClickListener {
            startActivity(
                Intent(this@SuccessActivity, UserHome::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
        binding.addPostBtn.setOnClickListener {
            startActivity(Intent(this@SuccessActivity, NewPostActivity::class.java))
        }
    }
}