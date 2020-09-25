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
import com.walky.walkys.adapters.FollowingListAdapter
import com.walky.walkys.databinding.ActivityFollowingListBinding
import java.util.ArrayList

class FollowingListActivity : AppCompatActivity() {

    lateinit var binding : ActivityFollowingListBinding
    private var adapter: FollowingListAdapter? = null
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_following_list)

        binding.header.titleTv.text = resources.getString(R.string.following_list)
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

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = FollowingListAdapter(this, arrayList)
        binding.recyclerView.adapter = adapter
    }
}