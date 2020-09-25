package com.walky.walkys.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.responses.HomeResponse
import com.walky.utils.Utils
import com.walky.utils.drawableBackColor
import com.walky.walkys.R
import com.walky.walkys.adapters.NotificationAdapter
import com.walky.walkys.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    lateinit var binding : ActivityNotificationBinding
    private var adapter : NotificationAdapter?=null
    private var arrayList : ArrayList<HomeResponse> = ArrayList()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_notification)


        binding.header.titleTv.text = resources.getString(R.string.notification)
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { finish() }

        binding.header.optionMenu.setOnClickListener {
            startActivity(Intent(this@NotificationActivity, NewPostActivity::class.java))
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        adapter = NotificationAdapter(this,arrayList)
        binding.recyclerView.adapter = adapter
    }
}