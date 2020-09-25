package com.walky.walkys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.responses.HomeResponse
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.adapters.GroupUserListAdapter
import com.walky.walkys.adapters.UserListAdapter
import com.walky.walkys.databinding.ActivityCreateGroupBinding
import java.util.ArrayList

class CreateGroupActivity : AppCompatActivity() {

    private var adapter: GroupUserListAdapter? = null
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    lateinit var binding : ActivityCreateGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_group)

        binding.header.titleTv.text = getString(R.string.create_group)
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { finish() }
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.setOnClickListener {
            startActivity(
                Intent(
                    this@CreateGroupActivity,
                    NewPostActivity::class.java
                )
            )
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this@CreateGroupActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = GroupUserListAdapter(this@CreateGroupActivity, arrayList)
        binding.recyclerView.adapter = adapter
    }
}