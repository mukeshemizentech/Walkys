package com.walky.walkys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.walky.utils.Utils
import com.walky.utils.shareIntent
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivityInviteBinding

class InviteActivity : AppCompatActivity() {

    lateinit var binding : ActivityInviteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_invite)

        binding.header.titleTv.text = resources.getString(R.string.invite_friends)
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { finish() }

        binding.header.optionMenu.setOnClickListener {
            startActivity(Intent(this@InviteActivity, NewPostActivity::class.java))
        }

        binding.btnShare.setOnClickListener{
            this.shareIntent(resources.getString(R.string.app_name),resources.getString(R.string.app_name))
        }
    }
}