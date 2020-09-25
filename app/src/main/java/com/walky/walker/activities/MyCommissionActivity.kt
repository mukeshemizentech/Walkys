package com.walky.walker.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.responses.HomeResponse
import com.walky.walker.adapters.MyCommisionAdaper
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivityMyCommissionBinding
import java.util.*

class MyCommissionActivity : AppCompatActivity() {


    lateinit var binding: ActivityMyCommissionBinding
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    private var adaper: MyCommisionAdaper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_commission)
        binding.headerCommon.backIv.setOnClickListener { onBackPressed() }
        binding.headerCommon.titleTv.setText(R.string.my_commission)
        binding.headerCommon.filterIv.visibility = View.GONE
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        adaper = MyCommisionAdaper(this, arrayList)
        binding.recyclerView.adapter = adaper
    }
}